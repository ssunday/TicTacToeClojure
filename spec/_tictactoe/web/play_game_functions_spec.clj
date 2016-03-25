(ns -tictactoe.web.play_game_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.web.play_game_functions :refer :all]
            [-tictactoe.ttt.scoring_schema :as schema]))

(describe "switch-player"
  (it "returns the not current player"
    (let [player-one "X"
          player-two "O"
          current-player "X"]
      (should= player-two (switch-player current-player player-one player-two)))))

(describe "player-won"

  (it "returns true if the particular player has won the game"
    (let [pl "X"
          op "O"
          board [0 pl 2
                 3 pl 5
                 6 pl 8]]
      (should (player-won board pl))))

  (it "returns false if the particular player has not won the game"
    (let [pl "X"
          op "O"
          board [0 pl 2
                 op pl 5
                 6 pl op]]
      (should-not (player-won board op))))

  (it "returns false if the game has not been won"
    (let [pl "X"
          op "O"
          board [0 1 2
                 op pl 5
                 6 pl op]]
      (should-not (player-won board pl)))))

(describe "current-player-is-ai"
  (it "returns true if the current player is an AI"
    (let [info {:player-one-marker "X"
                :player-two-marker "O"
                :current-player "X"
                :player-one-is-ai true
                :player-two-is-ai false}]
      (should (current-player-is-ai info))))

  (it "returns false if the current player is not an AI"
    (let [info {:player-one-marker "X"
                :player-two-marker "O"
                :current-player "O"
                :player-one-is-ai true
                :player-two-is-ai false}]
      (should-not (current-player-is-ai info)))))

(describe "mark-board"

  (it "returns board marked with spot if spot is supplied"
    (let [spot 0
          board [0 1 2 3 4 5 6 7 8]
          current-player "X"
          other-player "O"]
      (should= [current-player 1 2 3 4 5 6 7 8] (mark-board board spot current-player other-player))))

  (it "returns board marked with ai spot if spot is nil"
    (let [spot nil
          board [0 1 2 3 4 5 6 7 8]
          current-player "X"
          other-player "O"]
      (should= [0 1 2 3 4 5 6 7 current-player] (mark-board board spot current-player other-player)))))

(describe "score-game-round"

  (it "when player one won returns a vector of both names with 1/0/0 and 0/1/0"
    (let [pl1-name "Bob"
          pl2-name "Jane"
          pl1 "X"
          pl2 "O"
          board [pl1 pl2 pl1
                 pl1 pl2 pl2
                 pl1 pl1 pl2]]
      (should= [[pl1-name {schema/wins 1 schema/losses 0 schema/draws 0}]
                [pl2-name {schema/wins 0 schema/losses 1 schema/draws 0}]] (score-game-round pl1-name pl2-name pl1 pl2 board))))

  (it "when player two won returns a vector of both names with 0/1/0 and 1/0/0"
    (let [pl1-name "Bob"
          pl2-name "Jane"
          pl1 "X"
          pl2 "O"
          board [pl2 pl1 pl2
                 pl2 pl1 pl1
                 pl2 pl2 pl1]]
      (should= [[pl1-name {schema/wins 0 schema/losses 1 schema/draws 0}]
                [pl2-name {schema/wins 1 schema/losses 0 schema/draws 0}]] (score-game-round pl1-name pl2-name pl1 pl2 board))))

  (it "when game is tied returns vector with names and scores of 0/0/1 and 0/0/1"
    (let [pl1-name "Bob"
          pl2-name "Jane"
          pl1 "X"
          pl2 "O"
          board [pl1 pl2 pl1
                 pl2 pl2 pl1
                 pl1 pl1 pl2]]
      (should= [[pl1-name {schema/wins 0 schema/losses 0 schema/draws 1}]
                [pl2-name {schema/wins 0 schema/losses 0 schema/draws 1}]] (score-game-round pl1-name pl2-name pl1 pl2 board)))))
