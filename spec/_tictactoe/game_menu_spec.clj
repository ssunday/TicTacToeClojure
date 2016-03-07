(ns -tictactoe.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.game_menu :refer :all]))

(describe "do_menu_option"
  (it "returns nil for option 1"
    (should= true (do_menu_option 1)))
  (it "returns nil for option 2"
    (should= nil (do_menu_option 2))))
