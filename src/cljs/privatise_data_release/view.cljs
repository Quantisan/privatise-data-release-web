(ns privatise-data-release.view
  (:require [privatise-data-release.gateway :as g]
            [reagent.core :as r :refer [atom]]
            [reagent-forms.core :refer [bind-fields]]
            [clojure.string :as s]
            [goog.string :as gstring]))

(defn base
  "base template"
  [& content]
  [:div.container
   [:div.header.clearfix
    [:nav
     [:ul.nav.nav-pills.pull-right
      [:li.active {:role :presentation} [:a {:href "#"} "Home"]]
      [:li {:role :presentation} [:a {:href "https://github.com/Quantisan/privatise-data-release-service" :target "_blank"} "Code on Github"]]]]
    [:h3.text-muted "Privatize Data Release - Proof of Concept"]]

   content

   [:footer.footer
    [:span "Version " (str js/projectVersion)]
    [:span.pull-right (gstring/unescapeEntities "&copy; ")
     [:a {:href "https://www.quantisan.com"} "Paul Lam"] " 2015"]]])

(def example-csv (->> [[1 0 0 1 1] [1 0 0 1 0] [1 1 1 0 0] [1 0 1 1 1] [1 1 0 0 1] [1 0 1 1 1] [0 0 0 0 1] [0 0 1 0 1] [1 1 1 1 0] [1 0 0 0 0]
                       [0 1 1 1 1] [1 0 0 0 0] [1 1 0 0 1] [0 1 0 1 0] [1 0 0 0 0] [1 0 1 1 1] [1 1 0 1 1] [0 1 1 0 1] [1 1 1 0 1] [1 0 0 1 0]
                       [0 0 1 1 1] [0 1 1 1 0] [0 1 1 0 1] [0 1 1 1 1] [1 1 0 1 0] [0 1 1 0 1] [0 1 0 0 1] [1 1 0 1 0] [1 0 0 1 1] [1 0 0 0 0]
                       [1 0 1 1 1] [0 0 0 0 1] [1 1 1 1 0] [0 1 1 1 1] [1 0 1 1 1] [0 0 0 0 1] [0 1 0 0 1] [1 0 0 1 0] [1 0 0 0 1] [1 1 0 0 1]
                       [0 0 1 1 1] [1 1 1 0 0] [1 1 1 1 0] [0 0 0 1 1] [0 0 0 0 0] [0 1 1 0 0] [0 0 1 1 0] [1 0 1 0 1] [1 0 0 1 0] [0 0 0 0 0]
                       [1 0 0 0 0] [1 1 1 1 1] [1 0 0 1 1] [0 1 1 0 0] [1 1 1 1 0] [1 1 0 0 0] [1 1 1 0 1] [1 0 1 1 0] [0 0 1 1 0] [0 1 1 0 0]
                       [0 0 1 1 0] [1 1 0 0 1] [0 1 1 1 0] [1 0 1 0 0] [0 0 0 0 0] [1 0 1 1 1] [0 0 0 0 1] [1 1 0 0 0] [1 0 0 0 0] [1 0 1 1 0]
                       [1 1 1 1 1] [1 1 0 1 1] [1 0 0 0 0] [0 1 1 1 0] [0 0 1 1 1] [1 0 1 1 1] [1 1 0 0 1] [0 1 0 1 0] [1 0 0 0 1] [0 0 1 1 0]
                       [1 1 1 1 0] [0 0 0 1 1] [1 0 1 1 1] [0 1 0 0 1] [1 1 1 1 0] [0 1 1 0 1] [1 0 1 0 1] [0 0 1 1 1] [1 0 1 0 0] [1 0 1 0 0]
                       [0 0 0 1 0] [0 1 0 0 0] [1 1 1 1 1] [0 0 1 1 0] [0 0 1 0 1] [0 0 0 1 0] [0 0 0 0 0] [1 1 0 1 1] [1 0 0 1 1] [0 0 1 1 1]]
                      (map #(s/join "," %))
                      (s/join "\n" )))

(defn form-group [[label-text label-id] input help-text]
  [:div.form-group
   [:label.col-sm-3.control-label {:for label-id} label-text]
   [:div.col-sm-9
    input
    [:span.help-block {:id :helpBlock} help-text]]])


(def input-form
  (form-group ["Paste your data" :input-data]
              [:textarea.form-control {:field :textarea
                                       :id :input-data
                                       :rows 10}]
              "Paste in your data in CSV format. Values can only be binary, 0 or 1. The default sample data can be thought of as data from 100 participants responding 5 Yes/No questions each."))


(defn input-block [form-data ra-status ra-loading?]
  [:div.jumbotron.accent {:key "jumbotron"}
   [:div.form-horizontal
    [bind-fields input-form form-data]
    [:div.form-group
     [:div.col-sm-offset-3.col-sm-9
      [:button.btn.btn-primary
       {:type :submit
        :on-click #(do (reset! ra-loading? true)
                       (g/post-data! form-data ra-status ra-loading?))}
       "Privatize"]
      " "
      [:button.btn.btn-default
       {:type :button
        :on-click #(do
                     (reset! form-data {:input-data example-csv})
                     (reset! ra-loading? false))}
       "Reset"]]]]])

(def output-form
  (form-group ["Privatized data" :output-data]
              [:textarea.form-control {:field :textarea
                                       :id :output-data
                                       :readonly true
                                       :rows 10}]
              "What are you seeing here is a differentially private synthetic data of your input data set. [1]"))

(defn output-block [form-data]
  [:div.jumbotron
   [:div.alert.alert-danger "This is an unsecure, proof of concept demonstration. Use at your own risk."]
   [bind-fields output-form form-data]
   [:div
    [:small "[1] Hardt, Ligett, and McSherry, " [:a {:href  "http://arxiv.org/abs/1012.4763"} "A simple and practical algorithm for differentially private data release"]
     ", Proc. 26th Neural Information Processing Systems (NIPS), 2012"]]])

(defn loading-block [ra-loading?]
  (when @ra-loading?
    [:div.loading.vertical-center
     [:img {:src "img/loading.gif"
            :width "100px"}]]))

(defn home-page []
  (let [form-data (atom {:input-data example-csv})
        ra-loading? (atom false)
        ra-status (atom nil)]
    (fn []
      (base
        [:div.row
         (when (:output-data @form-data)
           [output-block form-data])]
        [:div.row
         (when (g/error? @ra-status)
           [:div.alert.alert-danger
            "Oh no! Looks like there's an error! Please "
            [:a {:href "mailto:paul@quantisan.com"} "contact support"]
            ". Status code: " @ra-status])
         [loading-block ra-loading?]]
        [:div.row
         [input-block form-data ra-status ra-loading?]]))))

