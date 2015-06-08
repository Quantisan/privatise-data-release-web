(ns privatise-data-release.handler
  (:require [privatise-data-release.routes.home :refer [home-routes]]
            [privatise-data-release.routes.api :refer [api-routes]]
            [privatise-data-release.middleware :as middleware]
            [compojure.core :refer [routes defroutes wrap-routes]]
            [compojure.route :refer [not-found resources]]))

(defroutes base-routes
  (resources "/")
  (not-found "Not Found"))

(def app
  (-> (routes
        api-routes
        (wrap-routes home-routes middleware/wrap-csrf)
        base-routes)
      middleware/wrap-base))

