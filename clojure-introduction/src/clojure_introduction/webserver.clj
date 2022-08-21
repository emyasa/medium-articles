(ns clojure-introduction.webserver
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty :refer [run-jetty]])
  (:import
   java.lang.management.ManagementFactory
   org.eclipse.jetty.jmx.MBeanContainer
   [org.eclipse.jetty.server ConnectorStatistics Server]
   org.eclipse.jetty.server.handler.StatisticsHandler))

(defrecord WebServer [port])

(defn init [config]
  (if-let [server-config (:server config)]
    (map->WebServer {:port (:port server-config)
                     :enabled true})
    (map->WebServer {:enabled false})))

(defn- jetty-conf
  [^Server server]
  (let [mb-container    (MBeanContainer. (ManagementFactory/getPlatformMBeanServer))
        default-handler (.getHandler server)
        stats-handler   (StatisticsHandler.)]
    (.addEventListener server mb-container)
    (.addBean server mb-container)
    (ConnectorStatistics/addToAllConnectors server)

    (.setHandler stats-handler default-handler)
    (.setHandler server stats-handler)
    (.addBean server stats-handler)

    (.setStopTimeout server 60000)
    (.setStopAtShutdown server true)))

(defrecord WebServerComponent [configurator]
  component/Lifecycle
  (start [this]
         (let [config (:config this)
               webserver (init config)
               port (:port webserver)]
           (when (:enabled webserver)
             (log/info "## Starting web server on port:" port)
             (assoc this :http-server
                    (run-jetty (fn [_] {:status 200}) (merge {:configurator configurator :join? false} {:port port}))))))
  (stop [this]
        (log/info "## Shutting down web server")
        (if-let [server (:http-server this)]
          (.stop server)
          (log/warn "Web server hasn't been running"))
        (dissoc this :http-server)))

(defn make [] (->WebServerComponent {:configurator jetty-conf }))
