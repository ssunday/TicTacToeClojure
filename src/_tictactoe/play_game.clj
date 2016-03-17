(ns -tictactoe.play_game
  (:require [-tictactoe.game_functions :as gf]
            [-tictactoe.console_io :as io]
            [-tictactoe.ai_player :as ai]
            [-tictactoe.score_recording :as score_recording]
            [-tictactoe.scoring_schema :as schema]))

(defn update-player-tally-win [player-tally player-one-won]
  (let [player-one-name (-> @player-tally first first)
        player-two-name (-> @player-tally second first)
        winning-player-name (if player-one-won player-one-name player-two-name)
        losing-player-name (if player-one-won player-two-name player-one-name)]
  (swap! player-tally update-in [winning-player-name schema/wins] inc)
  (swap! player-tally update-in [losing-player-name schema/losses] inc)))

(defn update-player-tally-draw [player-tally]
  (dorun (map #(swap! player-tally update-in [(first %) schema/draws] inc) @player-tally)))

(defn winning-player-is-player-one [current-player-marker player-one-marker]
  (not= current-player-marker player-one-marker))

(defn display-who-won [is-player-one-the-winner]
  (if is-player-one-the-winner
      (io/player-one-won-message)
      (io/player-two-won-message)))

(defn end-game-round [board player-tally current-player-marker player-one-marker]
  (if (gf/game-is-won board)
      (let [is-player-one-the-winner (winning-player-is-player-one current-player-marker player-one-marker)]
          (display-who-won is-player-one-the-winner)
          (update-player-tally-win player-tally is-player-one-the-winner))
      (do (io/game-is-tied-message)
          (update-player-tally-draw player-tally))))

(defn mark-board [board current-player-marker other-player-marker current-player-is-ai]
  (let [spot-to-be-marked (if current-player-is-ai (ai/best-move board current-player-marker other-player-marker)
                                                   (io/get-player-spot-to-be-marked board))]
      (gf/mark-board-location board spot-to-be-marked current-player-marker)))

(defn get-other-player-marker [first-player player-one-marker player-two-marker]
  (if (= first-player player-one-marker)
      player-two-marker
      player-one-marker))

(defn ask-for-player-ai-if-valid-board-dimension [board-dimension prompt]
  (if (= board-dimension 3)
    (prompt)
    false))

(defn play-game [player-tally]
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
                         (mark-board board player-marker other-player-marker
                         (if (= player-marker player-one-marker) player-one-is-ai player-two-is-ai))))
              (do (io/display-game-board board player-one-marker)
                  (end-game-round board player-tally player-marker player-one-marker)
                  (if (io/ask-if-player-wants-to-play-again-with-same-input)
                    (recur first-player (get-other-player-marker first-player player-one-marker player-two-marker)
                                        (gf/make-default-board board-dimension))))))))

(defn run-game []
  (io/display-currently-registered-names (score_recording/player-names))
  (let [player-one-name (io/get-player-one-name)
        player-two-name (io/get-player-two-name player-one-name)
        player-tally (-> [player-one-name player-two-name]
                         (zipmap (repeat schema/default-wins-losses-draws-scores))
                         atom)]
    (loop [play-again true]
      (if play-again
          (do (play-game player-tally)
              (recur (io/ask-if-player-wants-to-play-again-new-input)))
       (do (score_recording/record-tally @player-tally)
           (io/end-game-message))))))
