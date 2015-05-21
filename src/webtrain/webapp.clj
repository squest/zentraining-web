(ns webtrain.webapp
  (:require [com.stuartsierra.component :as com]))

(defrecord WebApp [host port]
  com/Lifecycle
  (start [this]
    (assoc this :url (str "http://" host ":" port)))
  (stop [this]
    this))

(defn make-webapp
  [host port]
  (map->WebApp {:host host :port port}))

