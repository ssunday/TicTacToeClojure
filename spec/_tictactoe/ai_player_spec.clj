(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(describe "get-available-locations"
  (it "returns only numbered, open spots"
    (should= (get-available-locations [0 "X" "O" 3 4 5 "X" "X" "O"]) '(0 3 4 5)))
)
