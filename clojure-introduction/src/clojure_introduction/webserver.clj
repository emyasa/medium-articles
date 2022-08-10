(ns clojure-introduction.webserver
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :refer [run-jetty]]))

(defrecord WebServer []
  component/Lifecycle
  (start [this]
         (log/info "## Starting web server")
         (assoc this :http-server
                (run-jetty {} {:port 3000})))
  (stop [this]
        (log/info "## Shutting down web server")
        (if-let [server (:htt-server this)]
          (.stop server)
          (log/warn "Web server hasn't been running"))
        (dissoc this :http-server)))

(defn web-server [] 
  (->WebServer))
