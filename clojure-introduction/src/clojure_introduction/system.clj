(ns clojure-introduction.system
  (:require [com.stuartsierra.component :as component]
            [clojure-introduction.webserver :as webserver]
            [clojure-introduction.redis :as redis]))

(defn system [system-config]
  (component/system-map
   :config system-config
   :web-server (component/using (webserver/make) [:config])
   :redis (component/using (redis/make) [:config])))