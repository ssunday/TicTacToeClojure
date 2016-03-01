(ns -tictactoe.game_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.game_functions :refer :all]))

(describe "make-default-board"
  (it "The default board is a vector of 0-8."
    (should= [0 1 2 3 4 5 6 7 8] (make-default-board))))

(describe "mark-board-location"
  (it "correctly marks given board given spot and marker."
    (should= [0 1 2 "X" 4 5 6 7 8] (mark-board-location (make-default-board) 3 "X"))))

(describe "game-is-won"
  (context "Game is won with horizontal match"
    (it "returns true"
      (game-is-won [0 1 2 "X" "X" "X" 6 7 8]))
  )

  (context "Game is won with vertical match"
    (it "returns true"
      (game-is-won [0 "X" 2 3 "X" 5 6 "X" 8]))
  )

  (context "Game is won with diagonal match"
    (it "returns true"
             (game-is-won ["X" 1 2
                           3 "X" 5
                           6 7 "X"])))
  (context "game has not been won or tied"
    (it "returns false"
    (not (game-is-won [0 "X" 2
                          "O" "X" 5
                          6 "O" 8])))
  )
  (context "game has been tied"
    (it "returns false"
        (not (game-is-won ["O" "X" "O"
                           "O" "X" "X"
                           "X" "O" "O"])))
  )
)


(describe "game-is-tied"
  (context "game is tied"
    (it "returns true"
      (game-is-tied ["O" "X" "O"
                         "O" "X" "X"
                         "X" "O" "O"])))

  (context "game has been won"
    (it "returns false"
        (not (game-is-tied ["O" "X" "O"
                            "O" "X" "X"
                            "O" "O" "X"])))
  )
)

(describe "game-is-won-or-tied"
  (it "returns true when game has been tied"
      (game-is-won-or-tied ["O" "X" "O"
                         "O" "X" "X"
                         "X" "O" "O"]))

  (it "returns true when game has been won"
      (game-is-won-or-tied ["O" "X" "O"
                     "O" "X" "X"
                     "O" "O" "X"]))
  (it "returns false when game neither been won or tied"
     (not (game-is-won-or-tied ["O" "X" 2
                    3 "X" 5
                    "O" 7 "X"])))

)
