(ns -tictactoe.web.route_controller
  (:require [noir.session :as session]
            [stencil.core :as stencil]
            [-tictactoe.web.board_presenter :as board]
            [-tictactoe.web.play_game_functions :as play]
            [-tictactoe.web.game_settings :as settings]
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
                                                    :see-scores (translate (loc) :menu/see-scores)}))

(defn- get-settings-from-previous-session []
    {:player-one-name (session/get :player-one-name)
     :player-two-name (session/get :player-two-name)
     :player-one-marker (session/get :player-one-marker)
     :player-two-marker (session/get :player-two-marker)
     :player-one-type (session/get :player-one-type)
     :player-two-type (session/get :player-two-type)
     :board-dimension (session/get :board-dimension)
     :first-player (session/get :first-player)})

(defn- preloaded-settings []
  (if (nil? (session/get :player-one-name))
      (settings/default-settings (translate (loc) :web/name))
      (get-settings-from-previous-session)))

(defn settings-page [bad-input]
  (let [base-settings (settings/default-settings (translate (loc) :web/name))
        board-dimension (settings/board-dimension-map (:board-dimension base-settings))
        first-player (settings/first-player-map (:first-player base-settings) (translate (loc) :web/player-one) (translate (loc) :web/player-two))
        player-one-type (settings/player-type-map (:player-one-type base-settings))
        player-two-type (settings/player-type-map (:player-two-type base-settings))
        markers-player-one (settings/markers-map (:player-one-marker base-settings))
        markers-player-two (settings/markers-map (:player-two-marker base-settings))]
  (stencil/render-file (resource-file-path "settings")  {:header (translate (loc) :web/game-settings)
                                                         :name (translate (loc) :web/name)
                                                         :player-one-name (:player-one-name base-settings)
                                                         :player-two-name (:player-two-name base-settings)
                                                         :marker (translate (loc) :web/marker)
                                                         :markers-player-one markers-player-one
                                                         :markers-player-two markers-player-two
                                                         :player-one-type player-one-type
                                                         :player-two-type player-two-type
                                                         :bad-input bad-input
                                                         :input-error (translate (loc) :web/input-error)
                                                         :choose-board-dimension (translate (loc) :web/choose-board-dimension)
                                                         :board-dimension board-dimension
                                                         :ai-works-for-3x3 (translate (loc) :web/ai-works-for-3x3)
                                                         :choose-player-that-goes-first (translate (loc) :web/choose-player-that-goes-first)
                                                         :first-player first-player
                                                         :next (translate (loc) :web/next)})))

(defn- play-game-page []
  (let [current-player-is-ai (play/current-player-is-ai {:player-one-marker (session/get :player-one-marker)
                                                         :player-two-marker (session/get :player-two-marker)
                                                         :player-one-is-ai (session/get :player-one-is-ai)
                                                         :player-two-is-ai (session/get :player-two-is-ai)
                                                         :current-player (session/get :current-player)})]
    (stencil/render-file (resource-file-path "play_game")
                         {:header (translate (loc) :menu/play-game)
                          :current-player-is-ai current-player-is-ai
                          :board (board/parse-board-for-display (session/get :board))
                          :next (translate (loc) :web/next)
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
                        :play-again (translate (loc) :web/play-again)
                        :see-scores (translate (loc) :menu/see-scores)}))

(defn- create-initial-session [params]
  (session/put! :player-one-name (:player-one-name params))
  (session/put! :player-two-name (:player-two-name params))
  (session/put! :player-one-marker (:player-one-marker params))
  (session/put! :player-two-marker (:player-two-marker params))
  (session/put! :player-one-type (:player-one-type params))
  (session/put! :player-two-type (:player-two-type params))
  (session/put! :player-one-is-ai (validation/player-is-ai (:player-one-type params) (:board-dimension params)))
  (session/put! :player-two-is-ai (validation/player-is-ai (:player-two-type params) (:board-dimension params)))
  (session/put! :current-player ((keyword (:first-player params)) params))
  (session/put! :first-player (:first-player params))
  (session/put! :board-dimension (:board-dimension params))
  (session/put! :board (play/make-board (convert-string-to-number (:board-dimension params)))))

(defn post-settings [params]
  (let [input-is-valid (validation/input-sanitation params)
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

(defn play-game [params data-storage]
  (let [spot (convert-string-to-number (:spot params))
        current-player-is-ai (play/current-player-is-ai {:player-one-marker (session/get :player-one-marker)
                                                         :player-two-marker (session/get :player-two-marker)
                                                         :player-one-is-ai (session/get :player-one-is-ai)
                                                         :player-two-is-ai (session/get :player-two-is-ai)
                                                         :current-player (session/get :current-player)})]
    (if (validation/spot-input-is-valid (session/get :board) spot current-player-is-ai)
        (do (game-turn spot)
            (if (play/game-is-over (session/get :board))
                (do (recording/record-tally @data-storage (play/score-game-round (session/get :player-one-name) (session/get :player-two-name)
                                                                                 (session/get :player-one-marker) (session/get :player-two-marker)
                                                                                 (session/get :board)))
                    (game-over-page))
                (play-game-page)))
        (play-game-page))))

(defn scores-page [data-storage]
  (let [tallys (reading/read-total-tally @data-storage)]
    (stencil/render-file (resource-file-path "scores") {:header (translate (loc) :web/game-scores)
                                                        :scores (scores/get-parsed-scores tallys)
                                                        :name-header (translate (loc) :web/player-header)
                                                        :tally-header (translate (loc) :output/tally-header)
                                                        :player-tally (translate (loc) :output/player-tally)
                                                        :return-home (translate (loc) :web/return-home)})))

(defn not-found-page []
  (stencil/render-file (resource-file-path "not_found") {:header (translate (loc) :web/page-not-found)
                                                         :sorry-page-not-found (translate (loc) :web/sorry-page-not-found)}))
