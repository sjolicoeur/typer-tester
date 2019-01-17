(ns typer-tester.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [typer-tester.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[typer-tester started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[typer-tester has shut down successfully]=-"))
   :middleware wrap-dev})
