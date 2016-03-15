(ns -tictactoe.game_runner-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.game_runner :refer :all])
  (:use [-tictactoe.game_menu :only (menu-options)]))

(describe "run"
  (around [it]
    (with-out-str (it)))

  (it "returns nil if the max menu option is returned"
    (should= nil (with-in-str (str (count menu-options)) (run)))))
