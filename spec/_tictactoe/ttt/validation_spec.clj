(ns -tictactoe.ttt.validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.validation :refer :all]))

(describe "invalid-board-dimension"

  (it "returns true when input is not in the valid-dimensions vector"
    (should (invalid-board-dimension "a")))

  (it "returns true when input is not in the valid-dimensions vector"
    (should (invalid-board-dimension "10")))

  (it "returns false when input is in the valid-dimensions vector"
    (should-not (invalid-board-dimension (first valid-dimensions))))

  (it "returns false when input is in the valid-dimensions vector"
    (should-not (invalid-board-dimension (second valid-dimensions)))))

(describe "marker-is-valid"

  (it "returns true when marker is a single letter character"
    (should (marker-is-valid "A")))

  (it "returns false when marker is an integer"
    (should-not (marker-is-valid "1")))

  (it "returns false when marker is blank"
    (should-not (marker-is-valid "")))

  (it "returns false when is more than one character long"
    (should-not (marker-is-valid "AB")))
)

(describe "first-player-invalid"

  (it "returns true for first player that matches neither of the players"
    (should (first-player-invalid "C" "A" "B")))

  (it "returns false for marker that matches one of the player markers"
    (should-not (first-player-invalid "player-one-marker" "player-one-marker" "player-two-marker"))))

(describe "spot-is-valid-location"

  (it "returns false for nil spot"
    (should-not (spot-is-valid-location [0 1 2 3 4 5 6 7 8] nil)))

  (it "returns false for spot not open in 3x3 board"
    (should-not (spot-is-valid-location ["X" 1 2 3 4 5 6 7 8] 0)))

  (it "returns false for spot not open in 4x4 board"
    (should-not (spot-is-valid-location [0 1 2 3 4 5 6 7 8 9 10 "X" 12 13 14 15] 11)))

  (it "returns true for spot open in 3x3 board"
    (should (spot-is-valid-location [0 1 2 3 4 5 6 7 8] 0)))

  (it "returns true for spot open in 4x4 board"
    (should (spot-is-valid-location [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] 0)))

  (it "returns false for spot not in 3x3 board"
    (should-not (spot-is-valid-location [0 1 2 3 4 5 6 7 8] 9)))

  (it "returns false for spot not in 4x4 board"
    (should-not (spot-is-valid-location [0 1 2 3 4 5 6 7 8] 20)))

  (it "returns true for spot in 3x3 board"
    (should (spot-is-valid-location [0 1 2 3 4 5 6 7 8] 3)))

  (it "returns true for spot in 4x4 board"
    (should (spot-is-valid-location [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] 14))))
