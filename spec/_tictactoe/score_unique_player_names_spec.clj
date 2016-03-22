(ns -tictactoe.score_unique_player_names-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_unique_player_names :refer :all]
            [-tictactoe.score_recording :as record]
            [-tictactoe.scoring_json :refer :all])
  (:use [-tictactoe.scoring_repository :only [alternate-file-name clear-all-data]]))

(def test-file-name "test")

(def test-data (->JSON))

(describe "player-names"

  (before
    (alternate-file-name test-data test-file-name)
    (clear-all-data test-data))

  (it "returns a vector of all the player names recorded in the file."
    (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Marge" {:wins 0, :losses 0, :draws 1}]
                  ["Sue" {:wins 1, :losses 0, :draws 1}]]
         names '("Sarah" "John" "Marge" "Sue")]
      (record/record-tally test-data tally1)
      (record/record-tally test-data tally2)
      (should= names (player-names test-data))))

  (it "returns player names if there is only one game set"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["Marge" {:wins 1, :losses 0, :draws 1}]]
          names '("Sarah" "Marge")]
      (record/record-tally test-data tally)
      (should= names (player-names test-data))))

  (it "only returns distinct names of multiple logged tallys"
    (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["Sue" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Sue" {:wins 1, :losses 0, :draws 1}]
                 ["Marge" {:wins 1, :losses 1, :draws 1}]]
         names '("Sarah" "Sue" "Marge")]
      (record/record-tally test-data tally1)
      (record/record-tally test-data tally2)
      (should= names (player-names test-data)))))
