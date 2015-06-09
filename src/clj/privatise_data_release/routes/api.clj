(ns privatise-data-release.routes.api
  (:require [privatise-data-release
             [transform :as t] [gateway :as g]]
            [ring.util.http-response :refer [ok]]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(s/defschema CsvData
  {:csv-data s/Str})

(defapi api-routes
  ;{:formats [:json-kw]}

  (swagger-ui "/api/reference")
  (swagger-docs
    {:info {:title "Data release API"}})

  (context* "/api" []

            (POST* "/data-release" []
                   :return   CsvData
                   :body     [csv CsvData]
                   :summary  "Reads in a CSV string and return the MWEM representation"
                   (ok {:csv-data (-> csv
                                      :csv-data
                                      t/parse-csv
                                      g/mwem
                                      t/int-matrix
                                      t/write-csv)}))))

