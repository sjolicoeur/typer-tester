(ns typer-tester.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[typer-tester started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[typer-tester has shut down successfully]=-"))
   :middleware identity})
