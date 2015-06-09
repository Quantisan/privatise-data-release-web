(ns privatise-data-release.transform
  (:require [clojure.string :as s]))

(defn- parse-row-integers [s]
  (->> (s/split s #",")
       (map s/trim)
       (map #(Integer. %))))

;; TODO use a CSV library
(defn parse-csv [s]
  (->> (s/split s #"\n")
       (map parse-row-integers)))

(defn int-matrix [coll]
  (seq (map #(map int %) coll)))

