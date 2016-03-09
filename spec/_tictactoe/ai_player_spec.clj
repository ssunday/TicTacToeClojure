(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(describe "get-available-locations"
  (it "returns only numbered, open spots"
    (should= [0 3 4 5] (get-available-spots [0 "X" "O" 3 4 5 "X" "X" "O"]))))

(describe "best-move"

  (it "returns corner spot on empty board"
    (should= 0 (best-move [0 1 2 3 4 5 6 7 8] "X" "O")))

  (it "returns top left corner spot when center is marked board"
    (should= 0 (best-move [0 1 2 3 "O" 5 6 7 8] "X" "O")))

  (it "returns winning spot on board where it has two in a row"
    (should= 2 (best-move ["X" "X" 2 3 4 5 6 7 8] "X" "O")))

  (it "returns winning spot on board where it has two in a row"
    (should= 6 (best-move ["X" "O" "O" "X" 4 5 6 7 8] "X" "O")))

  (it "blocks winning spot on board where opponent has two in a row"
    (should= 2 (best-move ["O" "O" 2 3 4 5 6 7 8] "X" "O")))

  (it "blocks winning spot on board where opponent has two in a row"
    (should= 8 (best-move ["O" "X" 2
                            3 "O" "X"
                            6 7 8] "X" "O")))

  (it "blocks opponent's winning spot even when it has 2 in a row."
    (should= 7 (best-move ["X" "O" "X"
                           "X" "O" 5
                           "O" 7 "X"] "X" "O")))

  (it "wins when it has the opportunity"
    (should= 8 (best-move ["X" "O" "O"
                            3 "X" 5
                            6 7 8] "X" "O")))

  (it "blocks opponent from winning in diagonal case L-R"
    (should= 8 (best-move ["O" "X" 2
                            3 "O" 5
                            "X"  7  8] "X" "O")))

  (it "blocks opponent from winning in diagonal case R-L"
    (should= 6 (best-move ["X" "X" "O"
                            3 "O" 5
                            6  7  8] "X" "O")))

  (it "blocks an opponent from winning second column"
    (should= 7 (best-move ["O" "O" "X"
                            "X" "O" 5
                            6 7 8] "X" "O")))

  (it "blocks an opponent from winning on top row horizontal"
    (should= 2 (best-move ["O" "O" 2
                            "X" 4 5
                            "X" 7 "O"] "X" "O")))


)
