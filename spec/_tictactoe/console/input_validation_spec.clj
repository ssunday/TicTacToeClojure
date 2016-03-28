(ns -tictactoe.console.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.console.input_validation :refer :all]))

(describe "first-player-marker-is-invalid"

  (it "returns true for marker that matches neither of the player markers"
    (first-player-marker-is-invalid "C" "A" "B"))
    
  (it "returns false for marker that matches one of the player markers"
    (not (first-player-marker-is-invalid "A" "A" "B"))))

(describe "spot-is-not-on-board"
  (it "returns true for spot not in 3x3 board"
    (spot-is-not-on-board [0 1 2 3 4 5 6 7 8] 9))

  (it "returns true for spot not in 4x4 board"
    (spot-is-not-on-board [0 1 2 3 4 5 6 7 8] 20))

  (it "returns true for spot in 3x3 board"
    (spot-is-not-on-board [0 1 2 3 4 5 6 7 8] 3))

  (it "returns true for spot in 4x4 board"
    (spot-is-not-on-board [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] 14)))

(describe "spot-is-invalid-input"
  (it "returns true for nil spot"
    (spot-is-invalid-input nil)))

(describe "spot-is-already-marked"
  (it "returns true for spot not open in 3x3 board"
    (spot-is-already-marked ["X" 1 2 3 4 5 6 7 8] 0))

  (it "returns true for spot not open in 4x4 board"
    (spot-is-already-marked [0 1 2 3 4 5 6 7 8 9 10 "X" 12 13 14 15] 11))

  (it "returns false for spot open in 3x3 board"
    (spot-is-already-marked [0 1 2 3 4 5 6 7 8] 0))

  (it "returns false for spot open in 4x4 board"
    (spot-is-already-marked [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] 0)))

(describe "invalid-board-dimension"

  (it "returns true when input is not in the valid-dimensions vector"
    (should (invalid-board-dimension "a")))

  (it "returns true when input is not in the valid-dimensions vector"
    (should (invalid-board-dimension "10")))

  (it "returns false when input is in the valid-dimensions vector"
    (should-not (invalid-board-dimension (first valid-dimensions))))

  (it "returns false when input is in the valid-dimensions vector"
    (should-not (invalid-board-dimension (second valid-dimensions)))))

(describe "y-or-n-response-is-invalid"

  (it "returns false for y when y is an option"
    (should-not (y-or-n-response-is-invalid "y" "y" "n")))

  (it "returns true for y when y is not an option"
    (should (y-or-n-response-is-invalid "y" "j" "n")))

  (it "returns true for n when n is not an option"
    (should (y-or-n-response-is-invalid "n" "j" "s")))

  (it "returns false for n when n is an option"
    (should-not (y-or-n-response-is-invalid "n" "j" "n"))))

(describe "menu-option-is-valid"

  (it "returns true for option that is within the menu length"
    (let [menu-length 3]
      (should (menu-option-is-valid menu-length 2))))

  (it "returns false for option that is beyond the menu length"
    (let [menu-length 3]
      (should-not (menu-option-is-valid menu-length 5))))

  (it "returns false for option that is not a number"
    (let [menu-length 3]
      (should-not (menu-option-is-valid menu-length nil)))))
