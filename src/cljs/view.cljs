(ns privatise-data-release.view
  (:require [goog.string :as gstring]))

(defn base
  "base template"
  [& content]
  [:div.container
   [:div.header.clearfix
    [:nav
     [:ul.nav.nav-pills.pull-right
      [:li.active {:role :presentation} [:a {:href "#"} "Home"]]]]
    [:h3.text-muted "Privatize Data Release"]]

   content

   [:footer.footer
    [:p (gstring/unescapeEntities "&copy; ")
     [:a {:href "https://www.quantisan.com"} "Paul Lam"]
     " 2015"]]])

(defn home-page []
  (base
    [:div.jumbotron
     [:form.form-horizontal
      [:div.form-group
       [:label.col-sm-3.control-label {:for :input-data} "Paste your data"]
       [:div.col-sm-9
        [:textarea {:id :input-data
                    :rows 10 :cols 55}]
        [:span.help-block {:id :helpBlock} "Paste in your data in CSV format. Values can only be binary, 0 or 1."]]]

      [:div.form-group
       [:div.col-sm-offset-3.col-sm-9
        [:button.btn.btn-primary {:type :submit} "Privatize"]
        " "
        [:button.btn.btn-default {:type :button} "Reset"]]]]]))

(defn result []
  (base
    [:div.alert.alert-danger "This is an unsecure, proof of concept demonstration. Use at your own risk."]

    ))
