(ns privatise-data-release.middleware
  (:require [clojure.java.io :as io]
            [environ.core :refer [env]]

            [ring.util.response :refer [redirect]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [prone.middleware :refer [wrap-exceptions]]))

(defn wrap-dev [handler]
  (if (env :dev?)
    (-> handler
        wrap-exceptions)
    handler))

(defn wrap-csrf [handler]
  (wrap-anti-forgery handler))

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        {:status 500
         :headers {"Content-Type" "text/html"}
         :body (-> "templates/error.html" io/resource slurp)}))))

(defn wrap-base [handler]
  (-> handler
      wrap-dev

      (wrap-defaults
        (-> site-defaults
            (assoc-in [:security :anti-forgery] false)))

      wrap-internal-error))

