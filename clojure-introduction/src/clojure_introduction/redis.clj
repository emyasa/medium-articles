(ns clojure-introduction.redis
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [taoensso.carmine :as car]))

(defrecord Redis [host port enabled])

(defn init [config]
  (if-let [redis-config (:redis config)]
    (map->Redis {:host (:host redis-config)
                 :port (:port redis-config)
                 :enabled true})
    (map->Redis {:enabled false})))

(defrecord RedisComponent []
  component/Lifecycle
  (start [this]
    (let [config (:config this)
          redis (init config)]
      (when (:enabled redis)
        (log/info "## Starting Redis Connection")
        (let [cache-conn
              {:host (:host redis)
               :port (:port redis)}]
          (car/wcar cache-conn (car/ping))))
      (assoc this :redis redis)))
  (stop [this]
    (dissoc this :redis)))

(defn make [] 
  (->RedisComponent))
