(ns -tictactoe.game_menu
  (:require [-tictactoe.display_previous_scores :refer :all]
            [-tictactoe.play_game :refer :all]))

(def game_menu {1 `(run-game)
                2 `(display-previous-scores)
                3 `(System/exit 0)})

(def menu_options {1 "play-game"
                   2 "see-scores"
                   3 "end-application"})

(defn get-menu-options []
  menu_options)

(defn do_menu_option [option]
  (eval (game_menu option)))
