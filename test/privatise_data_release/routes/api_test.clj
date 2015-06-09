(ns privatise-data-release.routes.api-test
  (:require [clojure.test :refer :all]
            [privatise-data-release.routes.api :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]))

(defn parse-body [body]
  (json/read-str (slurp body) :key-fn keyword))

(deftest post-data-release-test
  (testing "Test POST request to /data-release returns expected response"
    (let [resp (api-routes (-> (mock/request :post "/api/data-release")
                               (mock/body (json/write-str {:csv-data "1,0,0\n0,0,1\n"}))
                               (mock/content-type "application/json")))]
      (is (= 200 (:status resp)))
      (let [data (:csv-data (parse-body (:body resp)))]
        (is (= 11 (count data))))))) ;; data is a string

