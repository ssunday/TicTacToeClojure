(ns -tictactoe.web.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :refer :all]
            [noir.session :as session]
            [-tictactoe.web.route_controller :refer :all]))

(def data-storage (atom nil))

(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/settings" [] (settings-page false))
  (POST "/settings" request (post-settings request))
  (POST "/play_game" request (play-game request data-storage))
  (GET "/scores" [] (scores-page data-storage))
  (route/not-found (not-found-page)))

(def app
  (session/wrap-noir-session (handler/site app-routes)))

(defn run [data]
  (reset! data-storage data)
  (run-jetty app {:port 4000 :join? false}))
