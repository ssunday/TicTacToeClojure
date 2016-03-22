(ns -tictactoe.score_reading-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_reading :refer :all]
            [-tictactoe.score_recording :as record]
            [-tictactoe.scoring_json :refer :all])
  (:use [-tictactoe.scoring_repository :only [alternate-file-name clear-all-data]]))

(def test-file-name "test")

(def test-data (->JSON))

(describe "read-total-tally"

  (before
    (alternate-file-name test-data test-file-name)
    (clear-all-data test-data))

  (it "saves multiple tallys to a file and combines tallys of the same player name"
    (let [tally1 [["Sarah" {:wins 0, :losses 1, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Sarah" {:wins 1, :losses 1, :draws 0}]
                  ["John" {:wins 1, :losses 1, :draws 0}]]
         total-tally [["Sarah" {:wins 1, :losses 2, :draws 1}]
                      ["John" {:wins 2, :losses 1, :draws 1}]]]
      (record/record-tally test-data tally1)
      (record/record-tally test-data tally2)
      (should= total-tally (read-total-tally test-data))))

  (it "with multiple tallys loggied it combines tallys of the same player name"
    (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Marge" {:wins 1, :losses 0, :draws 1}]
                 ["John" {:wins 0, :losses 1, :draws 1}]]
         total-tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                      ["John" {:wins 1, :losses 1, :draws 2}]
                      ["Marge" {:wins 1, :losses 0, :draws 1}]]]
      (record/record-tally test-data tally1)
      (record/record-tally test-data tally2)
      (should= total-tally (read-total-tally test-data))))

  (it "returns the same set of data if there were no duplicate player names"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]]
      (record/record-tally test-data tally)
      (should= tally (read-total-tally test-data)))))
