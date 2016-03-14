(ns -tictactoe.display_previous_scores
  (:require [-tictactoe.console_io :as io]
            [-tictactoe.score_recording :as scores]))


(defn display-previous-scores []
  (io/display-tally (scores/read-total-tally)))
