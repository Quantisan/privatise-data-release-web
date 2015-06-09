(ns privatise-data-release.server
  (:require [privatise-data-release.handler :refer [app]]
            [environ.core :refer [env]]
            [ring.middleware.reload :as reload]
            [org.httpkit.server :refer [run-server]])
  (:gen-class))

(defn -main [& args]
  (let [port (Integer/parseInt (or (env :port) "3000"))]
    (run-server
      (if (env :dev?)
        (reload/wrap-reload #'app {:dirs ["src/clj"]})
        app)
      {:port port :join? false})))

