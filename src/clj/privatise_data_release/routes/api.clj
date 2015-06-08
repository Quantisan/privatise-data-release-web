(ns privatise-data-release.routes.api
  (:require [ring.util.http-response :refer [ok]]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(s/defschema CsvData
  {:data s/Str})

(defapi api-routes
  (swagger-ui "/api/swagger-ui")
  (swagger-docs
    {:info {:title "Data release API"}})

  (context* "/api" []

            (POST* "/echo" []
                   :return   (s/maybe CsvData)
                   :body     [csv (s/maybe CsvData)]
                   :summary  "echoes a CsvData from json-body"
                   (ok csv))))

