(ns -tictactoe.web.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer :all]
            [stencil.core :as stencil]
            [-tictactoe.web.play_game_presenter :as play-game]
            [-tictactoe.ttt.game_functions :as gf])
  (:use [ring.middleware.params :only [wrap-params]]))

(defn- file-route [name]
  (str "../resources/public/" name))

(defn- validate-input [player_one_marker player_two_marker]
  (not= player_one_marker player_two_marker))

(defn- post-settings [player_one_name player_two_name
                      player_one_marker player_two_marker
                      player_one_type player_two_type first_player]
  (if (validate-input player_one_marker player_two_marker)
      (stencil/render-file (file-route "play_game") {:header "Play Game"})
      (stencil/render-file (file-route "settings") {:header "Settings"})))

(defroutes app-routes
  (GET "/" [] (stencil/render-file (file-route "home") {:header "Welcome to Tic Tac Toe"}))
  (GET "/settings" [] (stencil/render-file (file-route "settings") {:header "Settings"}))
  (POST "/settings" [player_one_name player_two_name
                     player_one_marker player_two_marker
                     player_one_type player_two_type first_player]
                     (post-settings player_one_name player_two_name
                                    player_one_marker player_two_marker
                                    player_one_type player_two_type first_player)
  (POST "/play_game" (stencil/render-file (file-route "play_game") {:header "Play Game"})
  (route/not-found (stencil/render-file (file-route "not_found") {:header "Page not found!"})))

(def app (wrap-params app-routes))

(defn run []
  (run-jetty app {:port 4000 :join? false}))
