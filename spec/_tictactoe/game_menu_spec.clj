(ns -tictactoe.game_menu-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.game_menu :refer :all]))

(it "menu options and game menu are of the same length"
  (should= (count menu-options) (count game-menu)))
