(ns clj-dropbox-oauth2.dropbox
  (:require [clj-oauth2.client :as oauth2]
            [cheshire.core :as json]))

(defn- credentials [access-token]
  {:access-token access-token :token-type "bearer"})

(defn- parse-body [raw]
  (json/parse-string (:body raw) true))

(defn- do-request
  ([client access-token url]
     (do-request client access-token url {}))
  ([client access-token url params]
     (let [credentials (credentials access-token)]
       (apply client [url (merge params {:oauth2 credentials})]))))

(defn search
  "Searches for a given query."
  ([access-token query] (search access-token :dropbox "" query))
  ([access-token root path query]
     (let [url (format "https://api.dropbox.com/1/search/%s/%s"
                       (name root)
                       path)
           q {:query query}]
       (parse-body (do-request oauth2/post access-token url {:form-params q})))))

(defn metadata
  "Retrieves file/folder metadata"
  [access-token path]
  (let [url (format "https://api.dropbox.com/1/metadata/auto/%s" path)]
    (parse-body (do-request oauth2/get access-token url))))

(defn files
  "Downloads a file"
  [access-token path]
  (let [url (format "https://api-content.dropbox.com/1/files/auto/%s" path)]
    (:body (do-request oauth2/get access-token url {:as :stream}))))

(defn account-info
  "Retrieves information about the user's account"
  [access-token]
  (parse-body (do-request oauth2/get access-token
                          "https://api.dropbox.com/1/account/info")))


(defn latest-cursor
  "Retrieves latest cursor"
  [access-token]
  (:cursor (parse-body (do-request oauth2/post access-token
                                   "https://api.dropbox.com/1/delta/latest_cursor"))))

(defn delta
  "Retrieves changes since the cursor"
  [access-token cursor]
  (parse-body (do-request oauth2/post access-token "https://api.dropbox.com/1/delta"
                          {:form-params {:cursor cursor}})))

(defn revisions
  "Retrieves revision history for a file"
  [access-token path]
  (let [url (format "https://api.dropbox.com/1/revisions/auto/%s" path)]
    (parse-body (do-request oauth2/get access-token url))))
