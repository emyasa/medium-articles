(ns clojure-introduction.system
  (:require [com.stuartsierra.component :as component]
            [clojure-introduction.webserver :as webserver]))

(defn system []
  (component/system-map
   :web-server (component/using (webserver/web-server) [])))