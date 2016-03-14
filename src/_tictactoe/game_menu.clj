(ns -tictactoe.game_menu
  (:use [-tictactoe.display_previous_scores :only (display-previous-scores)]
        [-tictactoe.play_game :only (run-game)]))

(def game-menu {1 `(run-game)
                2 `(display-previous-scores)
                3 `(System/exit 0)})

(def menu-options {1 "play-game"
                   2 "see-scores"
                   3 "end-application"})

(defn do-menu-option [option]
  (eval (game-menu option)))
