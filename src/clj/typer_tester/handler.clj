(ns typer-tester.handler
  (:require [typer-tester.middleware :as middleware]
            [typer-tester.layout :refer [error-page]]
            [typer-tester.routes.home :refer [home-routes]]
            [typer-tester.routes.oauth :refer [oauth-routes]]
            [compojure.core :refer [routes wrap-routes]]
            [ring.util.http-response :as response]
            [compojure.route :as route]
            [typer-tester.env :refer [defaults]]
            [mount.core :as mount]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (middleware/wrap-base
    (routes
      (-> #'home-routes
          (wrap-routes middleware/wrap-csrf)
          (wrap-routes middleware/wrap-formats))
          #'oauth-routes
      (route/not-found
        (:body
          (error-page {:status 404
                       :title "page not found"}))))))

