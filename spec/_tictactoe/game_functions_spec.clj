(ns -tictactoe.game_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.game_functions :refer :all]))

(describe "make-default-board"
  (it "The default board for a 3x3 is a vector of 0-8."
    (should= [0 1 2 3 4 5 6 7 8] (make-default-board 3))
  (it "The default board for a 4x4 is a vector of 0-15."
    (should= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] (make-default-board 4)))))

(describe "mark-board-location"
  (it "correctly marks 3x3 board given spot and marker."
    (should= [0 1 2 "X" 4 5 6 7 8] (mark-board-location (make-default-board 3) 3 "X"))
  (it "correctly marks 4x4 board given spot and marker."
    (should= [0 1 2 "X" 4 5 6 7 8 9 10 11 12 13 14 15] (mark-board-location (make-default-board 3) 3 "X")))))

(describe "game-is-won"
  (context "Game is won with horizontal match"
    (it "returns true for 3x3 board"
      (game-is-won [0 1 2 "X" "X" "X" 6 7 8]))
    (it "returns true for 4x4 board"
      (game-is-won ["X" "X" "X" "X" 4 5 6 7 8 9 10 11 12 13 14 15])))

  (context "Game is won with vertical match"
    (it "returns true for 3x3 board"
      (game-is-won [0 "X" 2 3 "X" 5 6 "X" 8]))
    (it "returns true for 4x4 board"
      (game-is-won [0 1 2 "X"
                    4 5 6 "X"
                    8 9 10 "X"
                    12 13 14 "X"])))

  (context "Game is won with diagonal match"
    (it "returns true for 3x3 board"
             (game-is-won ["X" 1 2
                           3 "X" 5
                           6 7 "X"]))
    (it "returns true for 4x4 board"
             (game-is-won [0 1 2 "X"
                           4 5 "X" 7
                           8 "X" 10 11
                           "X" 13 14 15])))

  (context "game has not been won"
    (it "returns false for 3x3 board"
     (not (game-is-won [0 "X" 2
                       "O" "X" 5
                        6 "O" 8])))
    (it "returns false for 4x4 board"
     (not (game-is-won ["O" 1 2 "X"
                        4 5 "X" 7
                        8 "X" 10 "O"
                        "O" 13 14 15]))))

  (context "game has been tied"
    (it "returns false for 3x3 board"
        (not (game-is-won ["O" "X" "O"
                           "O" "X" "X"
                           "X" "O" "O"]))
    (it "returns false for 4x4 board"
        (not (game-is-won ["O" "O" "X" "X"
                           "O" "X" "O" "O"
                           "X" "O" "O" "X"
                           "X" "O" "O" "X"]))
                           ))))

(describe "game-is-tied"
  (context "game is tied"
    (it "returns true for 3x3 board"
      (game-is-tied ["O" "X" "O"
                     "O" "X" "X"
                     "X" "O" "O"]))

    (it "returns true for 4x4 board"
      (game-is-tied ["O" "O" "X" "X"
                     "O" "X" "O" "O"
                     "X" "O" "O" "X"
                     "X" "O" "O" "X"])))

  (context "game has been won"
    (it "returns false for 3x3 board"
        (not (game-is-tied ["O" "X" "O"
                            "O" "X" "X"
                            "O" "O" "X"])))
    (it "returns false for 4x4 board"
         (not (game-is-tied [0 1 2 "X"
                            4 5 "X" 7
                            8 "X" 10 11
                            "X" 13 14 15])))))

(describe "game-is-won-or-tied"
  (it "returns true when game has been tied for 3x3 board"
      (game-is-won-or-tied ["O" "X" "O"
                            "O" "X" "X"
                            "X" "O" "O"]))

  (it "returns true when game has been tied for 4x4 board"
      (game-is-won-or-tied ["O" "O" "X" "X"
                           "O" "X" "O" "O"
                           "X" "O" "O" "X"
                           "X" "O" "O" "X"]))

  (it "returns true when game has been won for 3x3 board"
      (game-is-won-or-tied ["O" "X" "O"
                            "O" "X" "X"
                            "O" "O" "X"]))

  (it "returns true when game has been won for 4x4 board"
      (game-is-won-or-tied ["O" "O" "X" "X"
                           "O" "X" "X" "O"
                           "O" "O" "X" "X"
                           "O" "X" "O" "X"]))

  (it "returns false when game neither been won or tied for 3x3 board"
     (not (game-is-won-or-tied ["O" "X" 2
                                3 "X" 5
                                "O" 7 "X"])))

  (it "returns false when game neither been won or tied for 4x4 board"
     (not (game-is-won-or-tied ["O" 1 2 "X"
                                4 5 "X" 7
                                8 "X" 10 "O"
                                "O" 13 14 15]))))
