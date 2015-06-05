(ns privatise-data-release.prod
  (:require [privatise-data-release.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
