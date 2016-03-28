(ns -tictactoe.web.board_presenter-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.web.board_presenter :refer :all]))

(describe "display-board"
  (it "returns string html representation of the board containing input fields when player is not ai"
    (let [board [0 1 2 3 4 5 6 7 "X"]
          current-player-is-ai false
          button-label "next"]
      (should (clojure.string/includes? (display-board board current-player-is-ai button-label) "input"))))

  (it "returns string html representation of the board containing no input fields when player is an ai"
    (let [board [0 1 2 3 4 5 6 7 "X"]
          current-player-is-ai true
          button-label "next"]
      (should-not (clojure.string/includes? (display-board board current-player-is-ai button-label) "input")))))
