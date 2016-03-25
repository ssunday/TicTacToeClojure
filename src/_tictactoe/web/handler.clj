(ns -tictactoe.web.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :refer :all]
            [noir.session :as session]
            [stencil.core :as stencil]
            [-tictactoe.web.play_game_presenter :as play-game]
            [-tictactoe.web.scores_presenter :as scores]
            [-tictactoe.ttt.locale :as locale]
            [-tictactoe.ttt.game_functions :as gf]
            [-tictactoe.ttt.ai_player :as ai]
            [-tictactoe.ttt.score_recording :as recording]
            [-tictactoe.ttt.score_reading :as reading]
            [-tictactoe.ttt.score_unique_player_names :as names]
            [-tictactoe.ttt.scoring_schema :as schema])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.convert_string_to_number :only (convert-string-to-number)]))

(def data-storage (atom nil))

(defn- file-route [name]
  (str "../resources/public/" name))

(defn- validate-input [player-one-marker player-two-marker player-one-name player-two-name board-dimension]
  (or (not= player-one-marker player-two-marker)
      (not= player-one-name player-two-name)))

(defn- player-is-ai [player-type board-dimension]
  (and (= player-type "AI")
       (= board-dimension "3")))

(defn- current-player-is-ai []
  (cond (and (= (session/get :player-one-marker) (session/get :current-player))
             (session/get :player-one-ai)) true
        (and (= (session/get :player-two-marker) (session/get :current-player))
             (session/get :player-two-ai)) true))

(defn- switch-player [current-player player-one-marker player-two-marker]
  (if (= current-player player-one-marker)
      player-two-marker
      player-one-marker))

(defn- create-initial-session [params]
  (session/put! :player-one-name (:player-one-name params))
  (session/put! :player-two-name (:player-two-name params))
  (session/put! :player-one-marker (:player-one-marker params))
  (session/put! :player-two-marker (:player-two-marker params))
  (session/put! :player-one-ai (player-is-ai (:player-one-type params) (:board-dimension params)))
  (session/put! :player-two-ai (player-is-ai (:player-two-type params) (:board-dimension params)))
  (session/put! :current-player ((keyword (:first-player params)) params))
  (session/put! :board (gf/make-default-board (convert-string-to-number (:board-dimension params)))))

(defn- player-one-won []
  (= (gf/game-is-won (session/get :board)) (session/get :player-one-marker)))

(defn- player-two-won []
  (= (gf/game-is-won (session/get :board)) (session/get :player-two-marker)))

(defn- game-over-message []
  (cond (player-one-won) (translate (locale/loc) :output/player-one-has-won)
        (player-two-won) (translate (locale/loc) :output/player-two-has-won)
        (gf/game-is-tied (session/get :board)) (translate (locale/loc) :output/game-has-been-tied)))

(defn- score-game-round []
  (let [player-one-name (session/get :player-one-name)
        player-two-name (session/get :player-two-name)
        winning-score {schema/wins 1 schema/losses 0 schema/draws 0}
        losing-score {schema/wins 0 schema/losses 1 schema/draws 0}
        draw-score {schema/wins 0 schema/losses 0 schema/draws 1}]
    (cond (player-one-won) (recording/record-tally @data-storage [[player-one-name winning-score]
                                                                  [player-two-name losing-score]])
          (player-two-won) (recording/record-tally @data-storage [[player-one-name losing-score]
                                                                  [player-two-name winning-score]])
          (gf/game-is-tied (session/get :board)) (recording/record-tally @data-storage [[player-one-name draw-score]
                                                                                        [player-two-name draw-score]]))))
(defn- play-game-page []
  (stencil/render-file (file-route "play_game")
                       {:header (translate (locale/loc) :menu/play-game)
                        :board (play-game/display-board (session/get :board) (current-player-is-ai) (translate (locale/loc) :web/next))
                        :current-marker-message (translate (locale/loc) :output/current-player-marker (session/get :current-player))}))

(defn- game-over-page []
  (stencil/render-file (file-route "game_over")
                       {:header (translate (locale/loc) :web/game-over)
                        :message (game-over-message)
                        :click-here (translate (locale/loc) :web/click-here)
                        :play-again (translate (locale/loc) :web/play-again)
                        :see-scores (translate (locale/loc) :menu/see-scores)}))

(defn- play-game [request]
  (let [{params :params} request]
    (let [other-player (switch-player (session/get :current-player)
                                      (session/get :player-one-marker)
                                      (session/get :player-two-marker))
          spot (if (:spot params)
                   (convert-string-to-number (:spot params))
                   (ai/best-move (session/get :board) (session/get :current-player) other-player))]

      (session/put! :board (gf/mark-board-location (session/get :board) spot (session/get :current-player)))
      (session/put! :current-player other-player))
    (if (gf/game-is-over (session/get :board))
        (do (score-game-round)
            (game-over-page))
        (play-game-page))))

(defn- home-page []
  (stencil/render-file (file-route "home") {:header (translate (locale/loc) :output/welcome-message)
                                            :play-game (translate (locale/loc) :menu/play-game)
                                            :scores (translate (locale/loc) :menu/see-scores)
                                            :click-here (translate (locale/loc) :web/click-here)}))

(defn- settings-page [error]
  (stencil/render-file (file-route "settings") {:header (translate (locale/loc) :web/game-settings)
                                                :set-player-names (translate (locale/loc) :web/set-player-names)
                                                :player-one-name (translate (locale/loc) :input/player-one-name)
                                                :player-two-name (translate (locale/loc) :input/player-two-name)
                                                :select-unique-markers (translate (locale/loc) :web/select-unique-markers)
                                                :set-player-one-marker (translate (locale/loc) :web/set-player-one-marker)
                                                :set-player-two-marker (translate (locale/loc) :web/set-player-two-marker)
                                                :error error
                                                :error-markers-are-same (translate (locale/loc) :web/error-markers-are-same)
                                                :choose-board-dimension (translate (locale/loc) :web/choose-board-dimension)
                                                :ai-works-for-3x3 (translate (locale/loc) :web/ai-works-for-3x3)
                                                :player-one-will-be-ai (translate (locale/loc) :web/player-one-will-be-ai)
                                                :player-one-will-be-human (translate (locale/loc) :web/player-one-will-be-human)
                                                :player-two-will-be-ai (translate (locale/loc) :web/player-two-will-be-ai)
                                                :player-two-will-be-human (translate (locale/loc) :web/player-two-will-be-human)
                                                :choose-player-that-goes-first (translate (locale/loc) :web/choose-player-that-goes-first)
                                                :player-one (translate (locale/loc) :web/player-one)
                                                :player-two (translate (locale/loc) :web/player-two)
                                                :next (translate (locale/loc) :web/next)}))

(defn- post-settings [request]
  (let [{params :params} request]
    (session/clear!)
    (if (validate-input (:player-one-marker params) (:player-two-marker params)
                        (:player-one-name params) (:player-two-name params) (:board-dimension params))
        (do (create-initial-session params)
            (play-game-page))
        (settings-page true))))

(defn- see-scores []
  (let [tallys (reading/read-total-tally @data-storage)]
    (stencil/render-file (file-route "scores") {:header (translate (locale/loc) :web/game-scores)
                                                :scores (scores/display-scores tallys (translate (locale/loc) :output/tally-header))
                                                :player-tally (translate (locale/loc) :output/player-tally)
                                                :click-here (translate (locale/loc) :web/click-here)
                                                :return-home (translate (locale/loc) :web/return-home)})))

(defn- page-not-found []
  (stencil/render-file (file-route "not_found") {:header (translate (locale/loc) :web/page-not-found)
                                                 :sorry-page-not-found (translate (locale/loc) :web/sorry-page-not-found)}))

(defroutes app-routes
  (GET "/" [] (home-page))
  (GET "/settings" [] (settings-page false))
  (POST "/settings" request (post-settings request))
  (POST "/play_game" request (play-game request))
  (GET "/scores" [] (see-scores))
  (route/not-found (page-not-found)))

(def app
  (session/wrap-noir-session (handler/site app-routes)))

(defn run [data]
  (reset! data-storage data)
  (run-jetty app {:port 4000 :join? false}))
