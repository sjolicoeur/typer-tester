(ns typer-tester.routes.oauth
  (:require [ring.util.http-response :refer [ok found]]
            [compojure.core :refer [defroutes GET]]
            [clojure.java.io :as io]
            [typer-tester.oauth :as oauth]
            [clojure.tools.logging :as log]))

(defn oauth-init
  "Initiates the Twitter OAuth"
  [request]
  (-> (oauth/fetch-request-token request)
      :oauth_token
      oauth/auth-redirect-uri
      found))

(defn oauth-callback
  "Handles the callback from Twitter."
  [{:keys [session params]}]
  ; oauth request was denied by user
  (if (:denied params)
    (-> (found "/")
        (assoc :flash {:denied true}))
    ; fetch the request token and do anything else you wanna do if not denied.
    (let [{:keys [user_id screen_name]} (oauth/fetch-access-token params)]
      (log/info "successfully authenticated as" user_id screen_name)
      (-> (found "/")
          (assoc :session
            (assoc session :user-id user_id :screen-name screen_name))))))


(defroutes oauth-routes
  (GET "/oauth/oauth-init" req (oauth-init req))
  (GET "/oauth/oauth-callback" [& req_token :as req] (oauth-callback req)))

