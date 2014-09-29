# Simple Clojure bindings to the Dropbox API with OAuth2

clj-dropbox-oauth2 provides clojure bindings to the Dropbox API. It
uses Oauth2 as authorization/authentication mechanism. However, it
does not provide any means to authenticate clients. Use
[friend-oauth2](https://github.com/ddellacosta/friend-oauth2) or
[clj-oauth2](https://github.com/sudharsh/clj-oauth2) to authenticate
users with Dropbox

The library depends upon `clj-oauth'.

## Usage

```clojure
(require '[clj-dropox-oauth2.dropbox :as dropbox])

;; request user's access token by other means...

(def found-files (dropbox/search access-token :dropbox "/path/to/directory" "*.pdf"))

(def file (dropbox/file access-token "/path/to/my/file.txt"))
```

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
