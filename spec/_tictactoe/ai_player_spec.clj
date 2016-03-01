(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(describe "get-available-locations"
  (it "returns only numbered, open spots"
    (should= [0 3 4 5] (get-available-locations [0 "X" "O" 3 4 5 "X" "X" "O"]))))


(describe "best-move"
  (it "returns corner spot on empty board"
    (should= 6 (best-move [0 1 2 3 4 5 6 7 8] "X" "O")))
  (it "returns winning spot on board where it has two in a row"
    (should= 2 (best-move ["X" "X" 2 3 4 5 6 7 8] "X" "O")))
  (it "blocks winning spot on board where opponent has two in a row"
    (should= 2 (best-move ["O" "O" 2 3 4 5 6 7 8] "X" "O")))
)
