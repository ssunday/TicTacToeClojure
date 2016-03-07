(ns -tictactoe.game_menu
  (:require [-tictactoe.display_previous_scores :refer :all]))

(def game_menu {1 "Play Game"
                2 "See Scores"})

(defn get-menu-options []
  game_menu)

(defn bad_menu_option []
    (throw (IllegalArgumentException.
          (str "This option does not exist"))))

(defn do_menu_option [option]
  (cond (= option 1) true
        (= option 2) (display-previous-scores)
        :else bad_menu_option))
