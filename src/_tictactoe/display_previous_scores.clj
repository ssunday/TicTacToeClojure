(ns -tictactoe.display_previous_scores
  (:require [-tictactoe.console_io :refer :all]
            [-tictactoe.score_recording :refer :all]
    ))

(defn display-previous-scores []
  (display-player-scores (read-all-scores)))
