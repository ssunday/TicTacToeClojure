(ns -tictactoe.play_game-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.play_game :refer :all]))

(describe "update-player-tally-win"

  (it "increments win by one for first player if they won and loss for second player by one"
    (let [player-tally (atom {"Sarah" {:wins 0 :losses 0 :draws 0} "John" {:wins 0 :losses 0 :draws 0}})]
      (update-player-tally-win player-tally true)
      (should= @player-tally {"Sarah" {:wins 1 :losses 0 :draws 0} "John" {:wins 0 :losses 1 :draws 0}})))

  (it "increments win by one for second player if they won and loss for first player by one"
    (let [player-tally (atom {"Sarah" {:wins 0 :losses 0 :draws 0} "John" {:wins 0 :losses 0 :draws 0}})]
      (update-player-tally-win player-tally false)
      (should= @player-tally {"Sarah" {:wins 0 :losses 1 :draws 0} "John" {:wins 1 :losses 0 :draws 0}}))))

(describe "update-player-tally-draw"
  (it "increments both player's draw tally by one"
    (let [player-tally (atom {"Sarah" {:wins 0 :losses 0 :draws 0} "John" {:wins 0 :losses 0 :draws 0}})]
      (update-player-tally-draw player-tally)
      (should= @player-tally {"Sarah" {:wins 0 :losses 0 :draws 1} "John" {:wins 0 :losses 0 :draws 1}}))))

(describe "winning-player-is-player-one"

  (it "returns false if the current player marker is equal to the player one marker"
    (let [player-one-marker "X"
          current-player-marker player-one-marker]
      (should-not (winning-player-is-player-one current-player-marker player-one-marker))))

  (it "returns true if the current player marker is not equal to the player one marker"
    (let [player-one-marker "X"
          current-player-marker "O"]
      (should (winning-player-is-player-one current-player-marker player-one-marker)))))

(describe "get-other-player-marker"
  (it "returns the marker that does not equal the supplied first player marker"
    (let [player-one-marker "X"
          player-two-marker "O"
          first-player player-one-marker]
      (should= player-two-marker (get-other-player-marker first-player player-one-marker player-two-marker)))

  (it "returns the marker that does not equal the supplied first player marker"
    (let [player-one-marker "X"
          player-two-marker "O"
          first-player player-two-marker]
      (should= player-one-marker (get-other-player-marker first-player player-one-marker player-two-marker))))))

(describe "mark-board"

  (it "for human player as current player returns board marked with provided input"
    (let [human-player "X"
          ai-player "O"]
      (should= [human-player 1 2 3 4 5 6 7 8] (with-in-str "0" (mark-board [0 1 2 3 4 5 6 7 8] human-player ai-player false)))))

  (it "for ai player as current player returns board marked with generated input"
    (let [ai "X"
          pl "O"]
      (should= [ai pl ai pl pl ai ai ai pl] (mark-board [ai pl 2 pl pl ai ai ai pl] ai pl true)))))
