(ns -tictactoe.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_recording :refer :all]))

(describe "record-tally"
  (it "saves it to a file to be retrieved"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]]]
      (clear-file)
      (record-tally tally)
      (should= tally (read-tally)))))

(describe "player-names"
  (it "gets a list of distinct player names"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                  ["John" {:wins 0, :losses 0, :draws 1}]
                  ["Bob" {:wins 0, :losses 0, :draws 1}]
                  ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
      (clear-file)
      (record-tally tally)
      (should= '("Sarah" "John" "Bob") (player-names)))))

(describe "read-tally"
  (it "gets a list of scores"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                  ["John" {:wins 0, :losses 0, :draws 1}]
                  ["Bob" {:wins 0, :losses 0, :draws 1}]
                  ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
      (clear-file)
      (record-tally tally)
      (should= tally (read-tally)))))

(describe "read-total-tally"
  (it "gets a list of total scores, combining player scores"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                  ["John" {:wins 0, :losses 0, :draws 1}]
                  ["Bob" {:wins 0, :losses 0, :draws 1}]
                  ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
      (clear-file)
      (record-tally tally)
      (should= (into {} [["Sarah" {:wins 0, :losses 0, :draws 2}]
                    ["John" {:wins 0, :losses 0, :draws 1}]
                    ["Bob" {:wins 0, :losses 0, :draws 1}]]) (read-total-tally)))))
