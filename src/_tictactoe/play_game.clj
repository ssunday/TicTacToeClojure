;Responsible for playing the games of tic tac toe.

(ns -tictactoe.play_game
  (:require [-tictactoe.play_game_functions :as play]
            [-tictactoe.game_functions :as gf]
            [-tictactoe.game_play_io :as io]
            [-tictactoe.score_unique_player_names :as names]
            [-tictactoe.score_recording :as score_recording]))

(defn- display-who-won [is-player-one-the-winner]
  (if is-player-one-the-winner
      (io/player-one-won-message)
      (io/player-two-won-message)))

(defn- end-game-round [board player-tally current-player-marker player-one-marker]
  (if (gf/game-is-won board)
      (let [is-player-one-the-winner (play/winning-player-is-player-one current-player-marker player-one-marker)]
          (display-who-won is-player-one-the-winner)
          (play/update-player-tally-win player-tally is-player-one-the-winner))
      (do (io/game-is-tied-message)
          (play/update-player-tally-draw player-tally))))

(defn- ask-for-player-ai-if-valid-board-dimension [board-dimension prompt]
  (if (= board-dimension 3)
    (prompt)
    false))

(defn- play-game [player-tally]
  (let [player-one-marker (io/get-player-one-marker)
        player-two-marker (io/get-player-two-marker player-one-marker)
        first-player (io/get-first-player player-one-marker player-two-marker)
        board-dimension (io/ask-for-board-dimension)
        player-one-is-ai (ask-for-player-ai-if-valid-board-dimension board-dimension io/get-whether-player-one-is-ai)
        player-two-is-ai (ask-for-player-ai-if-valid-board-dimension board-dimension io/get-whether-player-two-is-ai)]
      (loop [player-marker first-player
             other-player-marker (get-other-player-marker first-player player-one-marker player-two-marker)
             board (gf/make-default-board board-dimension)]
          (if (not (gf/game-is-over board))
              (do (io/display-current-player-marker player-marker)
                  (io/display-game-board board player-one-marker)
                  (recur other-player-marker player-marker
                         (gf/mark-board-location board
                                                (play/get-spot-to-be-marked board player-marker other-player-marker
                                                      (if (= player-marker player-one-marker)
                                                          player-one-is-ai
                                                          player-two-is-ai))
                                                player-marker)))
              (do (io/display-game-board board player-one-marker)
                  (end-game-round board player-tally player-marker player-one-marker)
                  (if (io/ask-if-player-wants-to-play-again-with-same-input)
                    (recur first-player (play/get-other-player-marker first-player player-one-marker player-two-marker)
                                        (gf/make-default-board board-dimension))))))))

(defn run-game []
  (io/display-currently-registered-names (names/player-names))
  (let [player-one-name (io/get-player-one-name)
        player-two-name (io/get-player-two-name player-one-name)
        player-tally (play/initial-player-tally player-one-name player-two-name)]
    (loop [play-again true]
      (if play-again
          (do (play-game player-tally)
              (recur (io/ask-if-player-wants-to-play-again-new-input)))
       (do (score_recording/record-tally @player-tally)
           (io/end-game-message))))))
