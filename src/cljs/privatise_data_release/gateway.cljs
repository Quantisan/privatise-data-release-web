(ns privatise-data-release.gateway
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn error? [status]
  (when (number? status)
    (or (< status 200) (>= status 400))))

(defn post-data [form-data ra-status ra-loading?]
  (go (let [{:keys [status error-code error-text]}
            (<! (http/post "/"
                           {:json-params {:input-data @form-data}}))]
        (reset! ra-status status)
        (reset! ra-loading? false))))

