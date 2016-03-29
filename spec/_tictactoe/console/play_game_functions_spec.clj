(ns -tictactoe.console.play_game_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.console.play_game_functions :refer :all]
            [-tictactoe.ttt.scoring_schema :as schema]))

(describe "update-player-tally-win"

  (it "increments win by one for first player if they won and loss for second player by one"
    (let [player-tally (atom {"Sarah" schema/default-wins-losses-draws-scores "John" schema/default-wins-losses-draws-scores})]
      (update-player-tally-win player-tally true)
      (should= @player-tally {"Sarah" {schema/wins 1 schema/losses 0 schema/draws 0} "John" {schema/wins 0 schema/losses 1 schema/draws 0}})))

  (it "increments win by one for second player if they won and loss for first player by one"
    (let [player-tally (atom {"Sarah" schema/default-wins-losses-draws-scores "John" schema/default-wins-losses-draws-scores})]
      (update-player-tally-win player-tally false)
      (should= @player-tally {"Sarah" {schema/wins 0 schema/losses 1 schema/draws 0} "John" {schema/wins 1 schema/losses 0 schema/draws 0}}))))

(describe "update-player-tally-draw"
  (it "increments both player's draw tally by one"
    (let [player-tally (atom {"Sarah" schema/default-wins-losses-draws-scores "John" schema/default-wins-losses-draws-scores})]
      (update-player-tally-draw player-tally)
      (should= @player-tally {"Sarah" {schema/wins 0 schema/losses 0 schema/draws 1} "John" {schema/wins 0 schema/losses 0 schema/draws 1}}))))

(describe "get-spot-to-be-marked"

  (around [it]
    (with-out-str (it)))

  (it "for human player as current player returns spot input"
    (let [human-player "X"
          ai-player "O"]
      (should= 0 (with-in-str "0" (get-spot-to-be-marked [0 1 2 3 4 5 6 7 8] human-player ai-player false)))))

  (it "for ai player returns best move"
    (let [ai "X"
          pl "O"]
      (should= 8 (get-spot-to-be-marked [0 1 2 3 4 5 6 7 8] ai pl true)))))

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

(describe "winning-player-is-player-one"

  (it "returns false if the current player marker is equal to the player one marker"
    (let [player-one-marker "X"
          current-player-marker player-one-marker]
      (should-not (winning-player-is-player-one current-player-marker player-one-marker))))

  (it "returns true if the current player marker is not equal to the player one marker"
    (let [player-one-marker "X"
          current-player-marker "O"]
      (should (winning-player-is-player-one current-player-marker player-one-marker)))))

(describe "initial-player-tally"
  (it "returns an atom hashmap of the names and scores of 0 for each category"
    (let [player-one "Sarah"
          player-two "John"
          initial-tally (initial-player-tally player-one player-two)]
      (should= (zipmap [player-one player-two] (repeat schema/default-wins-losses-draws-scores)) @initial-tally))))
