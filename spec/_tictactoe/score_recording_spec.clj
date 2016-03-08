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
