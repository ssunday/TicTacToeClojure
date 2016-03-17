(ns -tictactoe.display_previous_scores
  (:use [-tictactoe.score_recording :only (read-total-tally)]
        [-tictactoe.console_io :only (display-tally)]))

(defn display-previous-scores []
  (-> (read-total-tally)
      (display-tally)))
