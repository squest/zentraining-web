(ns webtrain.core
  (:require
    [com.stuartsierra.component :as com]
    [webtrain.config :refer [config]]
    [webtrain.couchbase :refer [make-couchbase]]
    [webtrain.webapp :refer [make-webapp]]
    [webtrain.routes :refer [make-routes]]))

(defn system
  "The main system for the app"
  [which-config?]
  (let [conf (which-config? config)
        {:keys [bucket uris]} (:couchbase conf)
        {:keys [host port]} (:webapp conf)]
    (com/system-map
      :couchbase (make-couchbase bucket uris)
      :routes (make-routes)
      :webapp (com/using (make-webapp host port)
                         [:couchbase]))))

(defn -main
  "Main gateway to the application"
  [& args]
  (let [[a] args]
    (condp = a
      "prod" (com/start (system :production))
      "dev" (com/start (system :development))
      "test" (com/start (system :test)))))
















