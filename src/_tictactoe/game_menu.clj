;Responsible for the main game menu and setting up the do-menu-option.

(ns -tictactoe.game_menu)

(def menu-options {1 "play-game"
                   2 "see-scores"
                   3 "end-application"})

(defmulti do-menu-option
  (fn [args] (keyword (get menu-options (:option args)))))
