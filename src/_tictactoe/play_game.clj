(ns -tictactoe.play_game
  (:require [-tictactoe.game_functions :as gf]
            [-tictactoe.console_io :as io]
            [-tictactoe.ai_player :as ai]
            [-tictactoe.score_recording :as score_recording]))


(defn update-player-tally-win [player-tally whether_player_one]
  (let [player-one-name (first (first @player-tally))
        player-two-name (first (second @player-tally))
        player-name (if whether_player_one player-one-name player-two-name)]
  (swap! player-tally update-in [player-name :wins] inc)))

(defn update-player-tally-loss [player-tally whether_player_one]
  (let [player-one-name (first (first @player-tally))
        player-two-name (first (second @player-tally))
        player-name (if whether_player_one player-one-name player-two-name)]
  (swap! player-tally update-in [player-name :losses] inc)))

(defn update-player-tally-draw [player-tally]
  (let [player-one-name (first (first @player-tally))
        player-two-name (first (second @player-tally))]
  (swap! player-tally update-in [player-one-name :draws] inc)
  (swap! player-tally update-in [player-two-name :draws] inc)))

(defn end-game-round [board player-tally current-player-marker player-one-marker]
  (if (gf/game-is-won board)
      (if (= current-player-marker player-one-marker)
          (do (io/player-two-won-message)
              (update-player-tally-loss player-tally true)
              (update-player-tally-win player-tally false))
          (do (io/player-one-won-message)
              (update-player-tally-loss player-tally false)
              (update-player-tally-win player-tally true)))
      (do (io/game-is-tied-message)
          (update-player-tally-draw player-tally))))

(defn get-move [board current-player-marker other-player-marker current-player-is-ai]
  (if current-player-is-ai
    (gf/mark-board-location board (ai/best-move board current-player-marker other-player-marker) current-player-marker)
    (gf/mark-board-location board (io/get-player-spot-to-be-marked board) current-player-marker)))

(defn play-game [player-tally]
  (let [player-one-marker (io/get-player-one-marker)
        player-two-marker (io/get-player-two-marker player-one-marker)
        first-player (io/get-first-player player-one-marker player-two-marker)
        player-one-is-ai (io/get-whether-player-one-is-ai)
        player-two-is-ai (io/get-whether-player-two-is-ai)
        board-dimension (io/ask-for-either-3x3-or-4x4-board)]
      (loop [player-marker first-player
             other-player-marker (if (= first-player player-one-marker) player-two-marker player-one-marker)
             board (gf/make-default-board board-dimension)]
          (if (not (gf/game-is-won-or-tied board))
              (do (io/display-current-player-marker player-marker)
                  (io/display-game-board board player-one-marker)
                  (recur other-player-marker player-marker
                        (get-move board player-marker other-player-marker
                            (if (= player-marker player-one-marker) player-one-is-ai player-two-is-ai))))
              (do (io/display-game-board board player-one-marker)
                  (end-game-round board player-tally player-marker player-one-marker)
                  (if (io/ask-if-player-wants-to-play-again-with-same-input)
                    (recur first-player (if (= first-player player-one-marker) player-two-marker player-one-marker)
                                        (gf/make-default-board board-dimension))))))))

(defn run-game []
  (io/display-currently-registered-names (score_recording/player-names))
  (let [player-one-name (io/get-player-one-name (score_recording/player-names))
        player-two-name (io/get-player-two-name (score_recording/player-names) player-one-name)
        player-tally  (atom (zipmap [player-one-name player-two-name] [{:wins 0 :losses 0 :draws 0} {:wins 0 :losses 0 :draws 0}]))]
    (loop [play-again true]
      (if play-again
          (do (play-game player-tally)
              (recur (io/ask-if-player-wants-to-play-again)))
       (do  (score_recording/record-tally @player-tally)
            (io/end-game-message))))))
