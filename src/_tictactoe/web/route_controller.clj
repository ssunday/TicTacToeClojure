(ns -tictactoe.web.route_controller
  (:require [noir.session :as session]
            [stencil.core :as stencil]
            [-tictactoe.web.board_presenter :as board]
            [-tictactoe.web.play_game_functions :as play]
            [-tictactoe.web.scores_presenter :as scores]
            [-tictactoe.web.input_validation :as validation]
            [-tictactoe.ttt.score_recording :as recording]
            [-tictactoe.ttt.score_reading :as reading])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.locale :only (loc)]
        [-tictactoe.ttt.convert_string_to_number :only (convert-string-to-number)]))

(defn- resource-file-path [name]
  (str "../resources/public/" name))

(defn home-page []
  (stencil/render-file (resource-file-path "home") {:header (translate (loc) :output/welcome-message)
                                                     :play-game (translate (loc) :menu/play-game)
                                                     :see-scores (translate (loc) :menu/see-scores)
                                                     :click-here (translate (loc) :web/click-here)}))

(defn settings-page [bad-input]
  (stencil/render-file (resource-file-path "settings") {:header (translate (loc) :web/game-settings)
                                                         :set-player-names (translate (loc) :web/set-player-names)
                                                         :player-one-name (translate (loc) :input/player-one-name)
                                                         :player-two-name (translate (loc) :input/player-two-name)
                                                         :select-unique-markers (translate (loc) :web/select-unique-markers)
                                                         :set-player-one-marker (translate (loc) :web/set-player-one-marker)
                                                         :set-player-two-marker (translate (loc) :web/set-player-two-marker)
                                                         :bad-input bad-input
                                                         :error-markers-or-names-are-same (translate (loc) :web/error-markers-or-names-are-same)
                                                         :choose-board-dimension (translate (loc) :web/choose-board-dimension)
                                                         :ai-works-for-3x3 (translate (loc) :web/ai-works-for-3x3)
                                                         :player-one-will-be-ai (translate (loc) :web/player-one-will-be-ai)
                                                         :player-one-will-be-human (translate (loc) :web/player-one-will-be-human)
                                                         :player-two-will-be-ai (translate (loc) :web/player-two-will-be-ai)
                                                         :player-two-will-be-human (translate (loc) :web/player-two-will-be-human)
                                                         :choose-player-that-goes-first (translate (loc) :web/choose-player-that-goes-first)
                                                         :player-one (translate (loc) :web/player-one)
                                                         :player-two (translate (loc) :web/player-two)
                                                         :next (translate (loc) :web/next)}))

(defn- play-game-page []
  (let [current-player-is-ai (play/current-player-is-ai {:player-one-marker (session/get :player-one-marker)
                                                         :player-two-marker (session/get :player-two-marker)
                                                         :player-one-is-ai (session/get :player-one-is-ai)
                                                         :player-two-is-ai (session/get :player-two-is-ai)
                                                         :current-player (session/get :current-player)})]
    (stencil/render-file (resource-file-path "play_game")
                         {:header (translate (loc) :menu/play-game)
                          :board (board/display-board (session/get :board) current-player-is-ai (translate (loc) :web/next))
                          :current-marker-message (translate (loc) :output/current-player-marker (session/get :current-player))})))

(defn- game-over-message []
  (let [board (session/get :board)]
    (cond (play/player-won board (session/get :player-one-marker)) (translate (loc) :output/player-one-has-won)
          (play/player-won board (session/get :player-two-marker)) (translate (loc) :output/player-two-has-won)
          (play/game-tied board) (translate (loc) :output/game-has-been-tied))))

(defn- game-over-page []
  (stencil/render-file (resource-file-path "game_over")
                       {:header (translate (loc) :web/game-over)
                        :message (game-over-message)
                        :click-here (translate (loc) :web/click-here)
                        :play-again (translate (loc) :web/play-again)
                        :see-scores (translate (loc) :menu/see-scores)}))

(defn- create-initial-session [params]
  (session/put! :player-one-name (:player-one-name params))
  (session/put! :player-two-name (:player-two-name params))
  (session/put! :player-one-marker (:player-one-marker params))
  (session/put! :player-two-marker (:player-two-marker params))
  (session/put! :player-one-is-ai (validation/player-is-ai (:player-one-type params) (:board-dimension params)))
  (session/put! :player-two-is-ai (validation/player-is-ai (:player-two-type params) (:board-dimension params)))
  (session/put! :current-player ((keyword (:first-player params)) params))
  (session/put! :board (play/make-board (convert-string-to-number (:board-dimension params)))))

(defn post-settings [request]
  (let [{params :params} request
        input-is-valid (validation/markers-and-names-are-not-the-same (:player-one-marker params) (:player-two-marker params)
                                                                      (:player-one-name params) (:player-two-name params))
        bad-input (not input-is-valid)]
    (if input-is-valid
        (do (create-initial-session params)
            (play-game-page))
        (settings-page bad-input))))

(defn- game-turn [spot]
  (let [current-player (session/get :current-player)
        other-player (play/switch-player current-player
                                         (session/get :player-one-marker)
                                         (session/get :player-two-marker))
        marked-board (play/mark-board (session/get :board) spot current-player other-player)]

    (session/put! :board marked-board)
    (session/put! :current-player other-player)))

(defn play-game [request data-storage]
  (let [{params :params} request
        spot (convert-string-to-number (:spot params))]
    (game-turn spot)
    (if (play/game-is-over (session/get :board))
        (do (recording/record-tally @data-storage (play/score-game-round (session/get :player-one-name) (session/get :player-two-name)
                                                                         (session/get :player-one-marker) (session/get :player-two-marker)
                                                                         (session/get :board)))
            (game-over-page))
        (play-game-page))))

(defn scores-page [data-storage]
  (let [tallys (reading/read-total-tally @data-storage)]
    (stencil/render-file (resource-file-path "scores") {:header (translate (loc) :web/game-scores)
                                                        :scores (scores/display-scores tallys (translate (loc) :output/tally-header))
                                                        :player-tally (translate (loc) :output/player-tally)
                                                        :click-here (translate (loc) :web/click-here)
                                                        :return-home (translate (loc) :web/return-home)})))

(defn not-found-page []
  (stencil/render-file (resource-file-path "not_found") {:header (translate (loc) :web/page-not-found)
                                                          :sorry-page-not-found (translate (loc) :web/sorry-page-not-found)}))
