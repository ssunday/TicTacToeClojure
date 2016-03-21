(ns -tictactoe.score_reading-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_reading :refer :all]
            [-tictactoe.score_recording :as record]
            [-tictactoe.score_data_functions_for_testing :refer :all]))

(def test-file-name "test")

(describe "read-total-tally"

  (before
    (different-file-name test-file-name)
    (clear-data))

  (it "saves multiple tallys to a file and combines tallys of the same player name"
    (let [tally1 [["Sarah" {:wins 0, :losses 1, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Sarah" {:wins 1, :losses 1, :draws 0}]
                  ["John" {:wins 1, :losses 1, :draws 0}]]
         total-tally [["Sarah" {:wins 1, :losses 2, :draws 1}]
                      ["John" {:wins 2, :losses 1, :draws 1}]]]
      (record/record-tally tally1)
      (record/record-tally tally2)
      (should= total-tally (read-total-tally))))

  (it "with multiple tallys loggied it combines tallys of the same player name"
    (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Marge" {:wins 1, :losses 0, :draws 1}]
                 ["John" {:wins 0, :losses 1, :draws 1}]]
         total-tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                      ["John" {:wins 1, :losses 1, :draws 2}]
                      ["Marge" {:wins 1, :losses 0, :draws 1}]]]
      (record/record-tally tally1)
      (record/record-tally tally2)
      (should= total-tally (read-total-tally))))

  (it "returns the same set of data if there were no duplicate player names"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]]
      (record/record-tally tally)
      (should= tally (read-total-tally)))))
