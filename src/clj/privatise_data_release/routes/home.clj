(ns privatise-data-release.routes.home
  (:require [hiccup.page :refer [html5 include-js include-css]]
            [hiccup.element :refer [javascript-tag link-to mail-to]]

            [ring.util.response :refer [redirect]]
            [compojure.core :refer [defroutes GET]]
            [environ.core :refer [env]]))

(def project-version (-> "project.clj" slurp read-string (nth 2)))

(defn base-template [body]
  (html5
    [:html {:lang "en"}
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
      [:meta {:name "viewport"
              :content "width=device-width, initial-scale=1"}]

      [:meta {:name :description :content "Privatize data release"}]
      [:meta {:name :author :content "Paul Lam"}]

      (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css")
      (include-css (if (env :dev?) "css/site.css" "css/site.min.css"))]

     body]))

(def data-release-demo
  (base-template
    [:body

     [:div#app]

     (javascript-tag (str "var projectVersion = '" project-version "';"))
     (include-js "https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js")
     (include-js "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js")
     (include-js "js/app.js")]))

(defroutes home-routes
  (GET "/" [] data-release-demo)
  (GET "/data-release-demo" [] (redirect "/")))

