(ns clojure-introduction.system
  (:require [com.stuartsierra.component :as component]
            [clojure-introduction.webserver :as webserver]))

(defn system [system-config]
  (component/system-map
   :config system-config
   :web-server (component/using (webserver/web-server) [:config])))