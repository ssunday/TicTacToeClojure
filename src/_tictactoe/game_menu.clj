(ns -tictactoe.game_menu
  (:require [-tictactoe.display_previous_scores :refer :all]
            [-tictactoe.play_game :refer :all]
            [-tictactoe.localization :refer :all]))

(def game_menu {1 "play-game"
                ;2 "see-scores"
                2 "end-application"})

(defn get-menu-options []
  game_menu)

(defn do_menu_option [option]
  (cond (= option 1) (run-game)
        ;(= option 2) (display-previous-scores)
        (= option 2) true))
