(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(describe "get-available-locations"
  (it "returns only numbered, open spots"
    (should= (get-available-locations [0 "X" "O" 3 4 5 "X" "X" "O"]) [0 3 4 5]))
)


(describe "best-move"
  (it "returns corner spot"
    (should= (best-move [0 1 2 3 4 5 6 7 8] "X" "O") 8))
)
