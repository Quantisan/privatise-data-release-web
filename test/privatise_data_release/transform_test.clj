(ns privatise-data-release.transform-test
  (:require [privatise-data-release.transform :refer :all]
            [clojure.test :refer :all]))

(deftest parse-csv-test
  (is (= [[1 2] [3 4]] (parse-csv "1,2\n3,4\n"))))

