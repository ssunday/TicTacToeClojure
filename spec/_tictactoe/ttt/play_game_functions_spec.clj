(ns -tictactoe.console.play_game_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.play_game_functions :refer :all]
            [-tictactoe.ttt.scoring_schema :as schema]))

(def pl "X")

(def op "O")

(describe "get-other-player-marker"
  (it "returns the marker that does not equal the supplied first player marker"
    (let [player-one-marker pl
          player-two-marker op
          first-player player-one-marker]
      (should= player-two-marker (get-other-player-marker first-player player-one-marker player-two-marker)))

  (it "returns the marker that does not equal the supplied first player marker"
    (let [player-one-marker pl
          player-two-marker op
          first-player player-two-marker]
      (should= player-one-marker (get-other-player-marker first-player player-one-marker player-two-marker))))))

(describe "mark-board"

  (it "returns board marked with spot with particular marker"
    (let [spot 0
          board [0 1 2 3 4 5 6 7 8]
          current-player pl]
      (should= [pl 1 2 3 4 5 6 7 8] (mark-board board spot current-player)))))

(describe "game-is-over"

    (it "returns true when game has been tied for 3x3 board"
        (game-is-over [op pl op
                       op pl pl
                       pl op op]))

    (it "returns true when game has been tied for 4x4 board"
        (game-is-over [op op pl pl
                       op pl op op
                       pl op op pl
                       pl op op pl]))

    (it "returns true when game has been won for 3x3 board"
        (game-is-over [op pl op
                       op pl pl
                       op op pl]))

    (it "returns true when game has been won for 4x4 board"
        (game-is-over [op op pl pl
                       op pl pl op
                       op op pl pl
                       op pl op pl])))

(describe "game-tied"

  (it "returns true for 3x3 board"
    (game-tied [op pl op
                op pl pl
                pl op op]))

  (it "returns true for 4x4 board"
    (game-tied [op op pl pl
                op pl op op
                pl op op pl
                pl op op pl])))

(describe "player-won"

  (it "returns true if the particular player has won the game"
    (let [board [0 pl 2
                 3 pl 5
                 6 pl 8]]
      (should (player-won board pl))))

  (it "returns false if the particular player has not won the game"
    (let [board [0 pl 2
                 op pl 5
                 6 pl op]]
      (should-not (player-won board op))))

  (it "returns false if the game has not been won"
    (let [board [0 1 2
                op pl 5
                6 pl op]]
      (should-not (player-won board pl)))))

(describe "current-player-is-ai"
  (it "returns true if the current player is an AI"
    (let [info {:player-one-marker pl
                :player-two-marker op
                :current-player pl
                :player-one-is-ai true
                :player-two-is-ai false}]
      (should (current-player-is-ai info))))

  (it "returns false if the current player is not an AI"
    (let [info {:player-one-marker pl
                :player-two-marker op
                :current-player op
                :player-one-is-ai true
                :player-two-is-ai false}]
      (should-not (current-player-is-ai info)))))

(describe "ai-move"
  (it "returns AI best move of 8 for default board"
    (should= 8 (ai-move [0 1 2 3 4 5 6 7 8] pl op))))

(describe "make-board"

  (it "returns a vector of 0 - 8 for a dimension of 3"
    (should= [0 1 2 3 4 5 6 7 8] (make-board 3)))

  (it "returns a vector of 0 - 15 for a dimension of 4"
    (should= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] (make-board 4))))

(describe "score-game-round"

  (it "when player one won returns a vector of both names with 1/0/0 and 0/1/0"
    (let [pl-name "Bob"
          op-name "Jane"
          board [pl op pl
                 pl op op
                 pl pl op]]
      (should= [[pl-name {schema/wins 1 schema/losses 0 schema/draws 0}]
                [op-name {schema/wins 0 schema/losses 1 schema/draws 0}]] (score-game-round {:player-one-name pl-name
                                                                                             :player-two-name op-name
                                                                                             :player-one-marker pl
                                                                                             :player-two-marker op
                                                                                             :board board}))))

  (it "when player two won returns a vector of both names with 0/1/0 and 1/0/0"
    (let [pl-name "Bob"
          op-name "Jane"
          board [op pl op
                 op pl pl
                 op op pl]]
      (should= [[pl-name {schema/wins 0 schema/losses 1 schema/draws 0}]
                [op-name {schema/wins 1 schema/losses 0 schema/draws 0}]] (score-game-round {:player-one-name pl-name
                                                                                             :player-two-name op-name
                                                                                             :player-one-marker pl
                                                                                             :player-two-marker op
                                                                                             :board board}))))

  (it "when game is tied returns vector with names and scores of 0/0/1 and 0/0/1"
    (let [pl-name "Bob"
          op-name "Jane"
          board [pl op pl
                 op op pl
                 pl pl op]]
      (should= [[pl-name {schema/wins 0 schema/losses 0 schema/draws 1}]
                [op-name {schema/wins 0 schema/losses 0 schema/draws 1}]] (score-game-round {:player-one-name pl-name
                                                                                             :player-two-name op-name
                                                                                             :player-one-marker pl
                                                                                             :player-two-marker op
                                                                                             :board board})))))
