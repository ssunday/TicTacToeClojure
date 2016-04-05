;Responsible for playing the games of tic tac toe.

(ns -tictactoe.console.play_game
  (:require [-tictactoe.console.player_input :as player]
            [-tictactoe.ttt.play_game_functions :as play]
            [-tictactoe.console.game_play_io :as io]
            [-tictactoe.ttt.score_unique_player_names :as names]
            [-tictactoe.ttt.score_recording :as score_recording]))

(defn- display-end-game-message [board player-one-marker player-two-marker]
  (cond (play/player-won board player-one-marker) (io/player-one-won-message)
        (play/player-won board player-two-marker) (io/player-two-won-message)
        (play/game-tied board) (io/game-is-tied-message)))

(defn- score-game [data board player-one-marker player-two-marker player-one-name player-two-name]
  (let [score (play/score-game-round {:player-one-name player-one-name :player-two-name player-two-name
                                      :player-one-marker player-one-marker :player-two-marker player-two-marker
                                      :board board})]
      (score_recording/record-tally data score)))

(defn- ask-for-player-ai-if-valid-board-dimension [board-dimension prompt]
  (if (= board-dimension 3)
    (prompt)))

(defn- get-spot-to-be-marked [board current-player other-player current-player-is-ai]
  (if current-player-is-ai
    (play/ai-move board current-player other-player)
    (player/get-player-spot-to-be-marked board)))

(defn- marked-board [board current-player other-player player-one-marker player-two-marker player-one-is-ai player-two-is-ai]
  (let [current-player-is-ai (play/current-player-is-ai {:player-one-marker player-one-marker :player-two-marker player-two-marker
                                                         :player-one-is-ai player-one-is-ai :player-two-is-ai player-two-is-ai
                                                         :current-player current-player})
        spot (get-spot-to-be-marked board current-player other-player current-player-is-ai)]
    (play/mark-board board spot current-player)))

(defn- play-game [data player-one-name player-two-name]
  (let [player-one-marker (io/get-player-one-marker)
        player-two-marker (io/get-player-two-marker player-one-marker)
        first-player (io/get-first-player player-one-marker player-two-marker)
        board-dimension (io/ask-for-board-dimension)
        player-one-is-ai (ask-for-player-ai-if-valid-board-dimension board-dimension io/get-whether-player-one-is-ai)
        player-two-is-ai (ask-for-player-ai-if-valid-board-dimension board-dimension io/get-whether-player-two-is-ai)]
      (loop [current-player first-player
             other-player (play/get-other-player-marker current-player player-one-marker player-two-marker)
             board (play/make-board board-dimension)]
          (io/display-current-player-marker current-player)
          (io/display-game-board board player-one-marker)
          (if (not (play/game-is-over board))
              (recur other-player current-player (marked-board board current-player other-player player-one-marker player-two-marker player-one-is-ai player-two-is-ai))
              (do (score-game data board player-one-marker player-two-marker player-one-name player-two-name)
                  (display-end-game-message board player-one-marker player-two-marker)
                  (if (io/ask-if-player-wants-to-play-again-with-same-input)
                      (recur first-player
                             (play/get-other-player-marker current-player player-one-marker player-two-marker)
                             (play/make-board board-dimension))))))))

(defn run-game [data]
  (io/display-currently-registered-names (names/player-names data))
  (let [player-one-name (io/get-player-one-name)
        player-two-name (io/get-player-two-name player-one-name)]
    (loop [play-again true]
      (if play-again
          (do (play-game data player-one-name player-two-name)
              (recur (io/ask-if-player-wants-to-play-again-new-input)))
          (io/end-game-message)))))
