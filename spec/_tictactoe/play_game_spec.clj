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

(describe "mark-board"

  (around [it]
    (with-out-str (it)))

  (it "for human player as current player returns board marked with provided input"
    (let [human-player "X"
          ai-player "O"]
      (should= [human-player 1 2 3 4 5 6 7 8] (with-in-str "0" (mark-board [0 1 2 3 4 5 6 7 8] human-player ai-player false)))))

  (it "for ai player as current player returns board marked with generated input"
    (let [ai "X"
          pl "O"]
      (should= [ai pl ai pl pl ai ai ai pl] (mark-board [ai pl 2 pl pl ai ai ai pl] ai pl true)))))
