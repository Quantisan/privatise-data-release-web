(ns privatise-data-release.routes.home
  (:require [hiccup.page :refer [html5 include-js include-css]]
            [hiccup.element :refer [javascript-tag link-to mail-to]]

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

(def home-page
  (html5
    [:html {:lang "en"}
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
      [:meta {:name "viewport"
              :content "width=device-width, initial-scale=1"}]

      [:title "Masking.io | Simple data masking"]
      [:meta {:name :description :content ""}]
      [:meta {:name :author :content "masking.io"}]

      (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css")
      (include-css (if (env :dev?) "css/site.css" "css/site.min.css"))]

     [:body
      [:div.container
       [:div.header.clearfix
        [:h3.text-muted "Masking.io"]]
       [:div.jumbotron.accent
        [:h2 "Simple data masking"]
        [:br]
        [:br]
        [:br]
        [:p
         (link-to {:class "typeform-share link btn btn-lg btn-success" :data-mode "1" :target "_blank"}
                  "https://quantisan.typeform.com/to/HNCBxS"
                  "Sign up for updates")
         (javascript-tag "(function(){var qs,js,q,s,d=document,gi=d.getElementById,ce=d.createElement,gt=d.getElementsByTagName,id='typef_orm',b='https://s3-eu-west-1.amazonaws.com/share.typeform.com/';if(!gi.call(d,id)){js=ce.call(d,'script');js.id=id;js.src=b+'share.js';q=gt.call(d,'script')[0];q.parentNode.insertBefore(js,q)}})()")]
        [:br]
        [:small (mail-to {} "info@masking.io")]]]]]))

(def data-release-demo
  (base-template
    [:body

     [:div#app]

     (javascript-tag (str "var projectVersion = '" project-version "';"))
     (include-js "https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js")
     (include-js "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js")
     (include-js "js/app.js")]))

(defroutes home-routes
  (GET "/" [] home-page)
  (GET "/data-release-demo" [] data-release-demo))

