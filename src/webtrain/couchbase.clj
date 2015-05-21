(ns webtrain.couchbase
  (:require [couchbase-clj.client :as cc]
            [com.stuartsierra.component :as com]))

(defrecord Couchbase [bucket uris connection]
  com/Lifecycle
  (start [this]
    (->> {:connection (cc/create-client {:bucket bucket :uris uris})}
         (merge this)))
  (stop [this]
    (do (cc/shutdown (:connection this) 200)
        (assoc this :connection nil))))

(defn make-couchbase
  "Creating and instance of couchbase"
  [bucket uris]
  (map->Couchbase {:bucket bucket :uris uris}))

