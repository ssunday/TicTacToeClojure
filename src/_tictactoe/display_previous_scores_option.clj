;Responsible for the menu option of displaying previous scores.

(ns -tictactoe.display_previous_scores_option
  (:require [-tictactoe.game_menu :as menu])
  (:use [-tictactoe.score_recording :only (read-total-tally)]
        [-tictactoe.console_io :only (display-tally)]))

(defmethod menu/do-menu-option :see-scores [data]
  (-> (read-total-tally)
      (display-tally)))
