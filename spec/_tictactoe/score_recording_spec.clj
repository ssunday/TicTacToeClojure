(ns -tictactoe.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_recording :refer :all]))

(def test-file-name "test.json")

(describe "record-tally"

  (it "saves a tally to an empty file"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]]]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally)
      (should= 1 (count (line-seq (clojure.java.io/reader test-file-name))))))

  (it "saves a set of tallys to an empty file"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 0, :losses 0, :draws 1}]]]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally)
      (should= 2 (count (line-seq (clojure.java.io/reader test-file-name))))))
)

(describe "read-tally"

  (it "returns nil for an empty file"
    (alternate-file-name test-file-name)
    (clear-file)
    (should= nil (read-tally)))

  (it "can retrieve multiple tallys as a vector"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally)
      (should= tally (read-tally))))

  (it "returns multiple sets of tallys as a vector with all of them on the same level"
    (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Marge" {:wins 1, :losses 1, :draws 1}]
                 ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally1)
      (record-tally tally2)
      (should= (into [] (concat tally1 tally2)) (read-tally)))))

(describe "player-names"

  (it "returns a vector of all the player names recorded in the file."
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
         names '("Sarah" "John")]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally)
      (should= names (player-names))))

  (it "returns player names if there is only one game set"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["Marge" {:wins 1, :losses 0, :draws 1}]]
         names '("Sarah" "Marge")]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally)
      (should= names (player-names))))

  (it "only returns distinct names of multiple logged tallys"
    (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["Sue" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Sue" {:wins 1, :losses 0, :draws 1}]
                 ["Marge" {:wins 1, :losses 1, :draws 1}]]
         names '("Sarah" "Sue" "Marge")]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally1)
      (record-tally tally2)
      (should= names (player-names)))))

(describe "read-total-tally"

  (it "saves multiple tallys to a file and combines tallys of the same player name"
    (let [tally1 [["Sarah" {:wins 0, :losses 1, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Sarah" {:wins 1, :losses 1, :draws 0}]
                  ["John" {:wins 1, :losses 1, :draws 0}]]
         total-tally [["Sarah" {:wins 1, :losses 2, :draws 1}]
                      ["John" {:wins 2, :losses 1, :draws 1}]]]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally1)
      (record-tally tally2)
      (should= total-tally (read-total-tally))))

  (it "with multiple tallys loggied it combines tallys of the same player name"
    (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]
          tally2 [["Marge" {:wins 1, :losses 0, :draws 1}]
                 ["John" {:wins 0, :losses 1, :draws 1}]]
         total-tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                      ["John" {:wins 1, :losses 1, :draws 2}]
                      ["Marge" {:wins 1, :losses 0, :draws 1}]]]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally1)
      (record-tally tally2)
      (should= total-tally (read-total-tally))))

  (it "returns the same set of data if there were no duplicate player names"
    (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]]
      (alternate-file-name test-file-name)
      (clear-file)
      (record-tally tally)
      (should= tally (read-total-tally)))))
