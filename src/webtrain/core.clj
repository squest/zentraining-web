(ns webtrain.core
  (:require
    [com.stuartsierra.component :as com]
    [couchbase-clj.client :as cc]
    [webtrain.config :refer [config]]))

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

(defrecord WebApp [host port]
  com/Lifecycle
  (start [this]
    (assoc this :url (str "http://" host ":" port)))
  (stop [this]
    this))

(defn make-webapp
  [host port]
  (map->WebApp {:host host :port port}))

(defn train-system
  [conf]
  (let [{:keys [bucket uris]} (:couchbase conf)
        {:keys [host port]} (:webapp conf)]
    (com/system-map
      :couchbase (make-couchbase bucket uris)
      :webapp (make-webapp host port))))














