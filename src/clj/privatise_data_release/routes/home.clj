(ns privatise-data-release.routes.home
  (:require [hiccup.page :refer [html5 include-js include-css]]
            [hiccup.element :refer [javascript-tag]]

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
      (include-css (if (env :dev?) "css/site.css" "css/site.min.css"))
      (javascript-tag (if (env :dev?)
                        "window.heap=window.heap||[],heap.load=function(t,e){window.heap.appid=t,window.heap.config=e;var a=document.createElement('script');a.type='text/javascript',a.async=!0,a.src=('https:'===document.location.protocol?'https:':'http:')+'//cdn.heapanalytics.com/js/heap-'+t+'.js';var n=document.getElementsByTagName('script')[0];n.parentNode.insertBefore(a,n);for(var o=function(t){return function(){heap.push([t].concat(Array.prototype.slice.call(arguments,0)))}},p=['clearEventProperties','identify','setEventProperties','track','unsetEventProperty'],c=0;c<p.length;c++)heap[p[c]]=o(p[c])};
                         heap.load('3087188651');"
                        "window.heap=window.heap||[],heap.load=function(t,e){window.heap.appid=t,window.heap.config=e;var a=document.createElement('script');a.type='text/javascript',a.async=!0,a.src=('https:'===document.location.protocol?'https:':'http:')+'//cdn.heapanalytics.com/js/heap-'+t+'.js';var n=document.getElementsByTagName('script')[0];n.parentNode.insertBefore(a,n);for(var o=function(t){return function(){heap.push([t].concat(Array.prototype.slice.call(arguments,0)))}},p=['clearEventProperties','identify','setEventProperties','track','unsetEventProperty'],c=0;c<p.length;c++)heap[p[c]]=o(p[c])};
                         heap.load('1246078048');"))]
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

