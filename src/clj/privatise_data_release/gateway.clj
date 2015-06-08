(ns privatise-data-release.gateway
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(def endpoints {:mwem "http://localdocker:9000"})

(defn mwem [coll]
  (let [options {:body (json/write-str coll)}
        {:keys [status error body]} @(http/post (str (:mwem endpoints) "/mwem") options)]
    (when-not error
      (:data (json/read-str body :key-fn keyword)))))

