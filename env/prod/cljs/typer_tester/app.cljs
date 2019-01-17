(ns typer-tester.app
  (:require [typer-tester.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
