(ns privatise-data-release.gateway
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn error? [status]
  (when (number? status)
    (or (< status 200) (>= status 400))))

(defn post-data! [form-data ra-status ra-loading?]
  (go (let [{:keys [body status error-code error-text]}
            (<! (http/post "/api/data-release"
                           {:json-params {:csv-data (:input-data @form-data)}}))]
        (reset! ra-status status)
        (reset! ra-loading? false)
        (swap! form-data assoc :output-data (:csv-data body)))))

