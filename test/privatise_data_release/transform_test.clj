(ns privatise-data-release.transform-test
  (:require [privatise-data-release.transform :refer :all]
            [clojure.test :refer :all]))

(deftest parse-csv-test
  (is (= [[1 0 0] [0 0 1]] (parse-csv "1,0,0\n0,0,1\n"))))

