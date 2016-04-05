;Responsible for the menu option of displaying previous scores.

(ns -tictactoe.console.display_previous_scores_option
  (:require [-tictactoe.console.game_menu :as menu]
            [-tictactoe.ttt.score_reading :as scores]
            [-tictactoe.console.display_tally :as display]))

(defmethod menu/do-menu-option :see-scores [args]
  (-> (:data args)
      scores/read-total-tally
      display/display-tally))
