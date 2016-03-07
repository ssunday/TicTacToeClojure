(ns -tictactoe.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.input_validation :refer :all]))

(describe "convert-string-to-number"
  (it "returns nil for non number"
    (should= (convert-string-to-number "A") nil))
  (it "returns nil for blank space"
    (should= (convert-string-to-number "") nil))
  (it "returns number for string number"
    (should= (convert-string-to-number "1") 1)))

(describe "check-if-marker-is-invalid"
  (it "returns true for marker longer than one digit in length"
    (convert-string-to-number "Abc"))
  (it "returns true for blank space"
    (convert-string-to-number ""))
  (it "returns true for string number"
    (convert-string-to-number "1"))
  (it "returns false for single character"
    (not (convert-string-to-number "A"))))

(describe "check-if-first-player-marker-is-invalid"
  (it "returns true for marker that matches neither of the player markers"
    (check-if-first-player-marker-is-invalid "C" "A" "B"))
  (it "returns false for marker that matches one of the player markers"
    (not (check-if-first-player-marker-is-invalid "A" "A" "B"))))

(describe "check-if-spot-is-not-on-board"
  (it "returns true for spot not in 3x3 board"
    (check-if-spot-is-not-on-board [0 1 2 3 4 5 6 7 8] 9))

  (it "returns true for spot not in 4x4 board"
    (check-if-spot-is-not-on-board [0 1 2 3 4 5 6 7 8] 20))

  (it "returns true for spot in 3x3 board"
    (check-if-spot-is-not-on-board [0 1 2 3 4 5 6 7 8] 3))

  (it "returns true for spot in 4x4 board"
    (check-if-spot-is-not-on-board [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] 14)))

(describe "check-if-spot-is-already-marked"
  (it "returns true for spot not open in 3x3 board"
    (check-if-spot-is-already-marked ["X" 1 2 3 4 5 6 7 8] 0))

  (it "returns true for spot not open in 4x4 board"
    (check-if-spot-is-already-marked [0 1 2 3 4 5 6 7 8 9 10 "X" 12 13 14 15] 11))

  (it "returns false for spot open in 3x3 board"
    (check-if-spot-is-already-marked [0 1 2 3 4 5 6 7 8] 0))

  (it "returns false for spot open in 4x4 board"
    (check-if-spot-is-already-marked [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] 0)))

(describe "check-if-spot-is-invalid-input"
  (it "returns true for nil spot"
    (check-if-spot-is-invalid-input [0 1 2 3 4 5 6 7 8] "A")))
