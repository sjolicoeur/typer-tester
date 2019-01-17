(ns user
  (:require [typer-tester.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [typer-tester.figwheel :refer [start-fw stop-fw cljs]]
            [typer-tester.core :refer [start-app]]
            [typer-tester.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'typer-tester.core/repl-server))

(defn stop []
  (mount/stop-except #'typer-tester.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'typer-tester.db.core/*db*)
  (mount/start #'typer-tester.db.core/*db*)
  (binding [*ns* 'typer-tester.db.core]
    (conman/bind-connection typer-tester.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


