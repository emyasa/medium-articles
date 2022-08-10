(ns clojure-introduction.config
  (:require [clojure.edn :as edn]))

(defn load-config []
  (edn/read-string (slurp "config/config.edn")))