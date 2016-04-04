(ns -tictactoe.web.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :refer :all]
            [-tictactoe.web.route_controller :refer :all])
  (:use [noir.cookies :only (wrap-noir-cookies)]
        [noir.session :only (wrap-noir-session)]))

(def data-storage (atom nil))

(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/settings" [] (settings-page false))
  (POST "/settings" request (post-settings (:params request)))
  (POST "/play_game" request (play-game (:params request) data-storage))
  (GET "/scores" [] (scores-page data-storage))
  (route/resources "/")
  (route/not-found (not-found-page)))

(def app
  (-> app-routes
      handler/site
      wrap-noir-session
      wrap-noir-cookies))

(defn run [data]
  (reset! data-storage data)
  (run-jetty app {:port 4000 :join? false}))
