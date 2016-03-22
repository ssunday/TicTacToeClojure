;Responsible for the menu option of displaying previous scores.

(ns -tictactoe.display_previous_scores_option
  (:require [-tictactoe.game_menu :as menu]
            [-tictactoe.score_reading :as scores]
            [-tictactoe.display_tally :as display]))

(defmethod menu/do-menu-option :see-scores [args]
  (-> (:data args)
      scores/read-total-tally
      (display/display-tally)))
