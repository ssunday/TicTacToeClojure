(ns -tictactoe.play_game
  (:require [-tictactoe.game_functions :as gf]
            [-tictactoe.console_io :as io]
            [-tictactoe.ai_player :as ai]
            [-tictactoe.score_recording :as score_recording]))

(defn update-player-scores-for-win [player-scores whether_player_one]
  (let [player-one-name (first (first @player-scores))
        player-two-name (first (second @player-scores))
        player-name (if whether_player_one player-one-name player-two-name)]
  (swap! player-scores update player-name inc)))

(defn end-game-round [board player-scores current-player-marker player-one-marker]
  (if (gf/game-is-won board)
      (if (= current-player-marker player-one-marker)
          (do (io/player-two-won-message)
              (update-player-scores-for-win player-scores false))
          (do (io/player-one-won-message)
              (update-player-scores-for-win player-scores true)))
      (io/game-is-tied-message)))

(defn get-move [board current-player-marker other-player-marker current-player-is-ai]
  (if current-player-is-ai
    (gf/mark-board-location board (ai/best-move board current-player-marker other-player-marker) current-player-marker)
    (gf/mark-board-location board (io/get-player-spot-to-be-marked board) current-player-marker)))

(defn play-game [player-scores]
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
                  (io/display-game-board board)
                  (recur other-player-marker player-marker
                        (get-move board player-marker other-player-marker
                            (if (= player-marker player-one-marker) player-one-is-ai player-two-is-ai))))
              (do (io/display-game-board board)
                  (end-game-round board player-scores player-marker player-one-marker)
                  (if (io/ask-if-player-wants-to-play-again-with-same-input)
                    (recur first-player (if (= first-player player-one-marker) player-two-marker player-one-marker)
                                        (gf/make-default-board board-dimension))))))))

(defn run-game []
  (let [player-one-name (io/get-player-one-name)
        player-two-name (io/get-player-two-name player-one-name)
        player-scores (atom (zipmap [player-one-name player-two-name] [0 0]))]
    (loop [play-again true]
      (if play-again
          (do (play-game player-scores)
              (recur (io/ask-if-player-wants-to-play-again)))
       (do (io/display-player-scores @player-scores)
            (score_recording/record-scores @player-scores)
            (io/end-game-message))))))
