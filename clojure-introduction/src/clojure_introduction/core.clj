(ns clojure-introduction.core
  (:require [clojure.tools.logging :as log]
            [clojure-introduction.config :as config]
            [clojure-introduction.system :as system]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defn start [system]
  (log/info "## Starting the system")
  (-> (config/load-config)
      system
      component/start))

(defn stop [system]
  (log/info "## Shutting down the system")
  (component/stop system))

(defn -main []
  (let [system (start system/system)]
    (.addShutdownHook (Runtime/getRuntime) (Thread. #(stop system)))))
