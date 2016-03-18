;Responsible for running the menu loop.

(ns -tictactoe.game_runner
  (:require [-tictactoe.scoring_edn :refer :all]
            [-tictactoe.scoring_json :refer :all]
            [-tictactoe.scoring_postgres :refer :all]
            [-tictactoe.play_game_option :refer :all]
            [-tictactoe.display_previous_scores_option :refer :all]
            [-tictactoe.end_application_option :refer :all])
  (:use [-tictactoe.console_io :only (start-game-message select-menu-option)]
        [-tictactoe.game_menu :only (do-menu-option menu-options)]))

(defn run []
  (start-game-message)
  (loop [menu-option (select-menu-option menu-options)]
    (do-menu-option menu-option)
    (recur (select-menu-option menu-options))))
