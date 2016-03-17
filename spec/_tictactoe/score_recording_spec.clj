(ns -tictactoe.score_recording-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_recording :refer :all]
            [-tictactoe.scoring_repository :refer :all]
            [-tictactoe.scoring_json :refer :all]
            [-tictactoe.scoring_edn :refer :all]))

(def test-file-name "test")

(context "EDN"

  (describe "player-names"

    (before
      (different-file-name test-file-name)
      (clear-data))

    (around [it]
      (with-redefs [data-type :edn]
        (it)))

    (it "returns a vector of all the player names recorded in the file."
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 1}]
                    ["Sue" {:wins 1, :losses 0, :draws 1}]]
           names '("Sarah" "John" "Marge" "Sue")]
        (record-tally tally)
        (record-tally tally2)
        (should= names (player-names))))

    (it "returns player names if there is only one game set"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["Marge" {:wins 1, :losses 0, :draws 1}]]
            names '("Sarah" "Marge")]
        (record-tally tally)
        (should= names (player-names))))

    (it "only returns distinct names of multiple logged tallys"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["Sue" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Sue" {:wins 1, :losses 0, :draws 1}]
                   ["Marge" {:wins 1, :losses 1, :draws 1}]]
           names '("Sarah" "Sue" "Marge")]
        (record-tally tally1)
        (record-tally tally2)
        (should= names (player-names)))))

    (describe "read-total-tally"

      (before
        (different-file-name test-file-name)
        (clear-data))

      (around [it]
        (with-redefs [data-type :edn]
          (it)))

      (it "saves multiple tallys to a file and combines tallys of the same player name"
        (let [tally1 [["Sarah" {:wins 0, :losses 1, :draws 1}]
                     ["John" {:wins 1, :losses 0, :draws 1}]]
              tally2 [["Sarah" {:wins 1, :losses 1, :draws 0}]
                      ["John" {:wins 1, :losses 1, :draws 0}]]
             total-tally [["Sarah" {:wins 1, :losses 2, :draws 1}]
                          ["John" {:wins 2, :losses 1, :draws 1}]]]
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
          (record-tally tally1)
          (record-tally tally2)
          (should= total-tally (read-total-tally))))

      (it "returns the same set of data if there were no duplicate player names"
        (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                     ["John" {:wins 1, :losses 0, :draws 1}]]]
          (record-tally tally)
          (should= tally (read-total-tally))))))

(context "JSON"

  (describe "player-names"

    (before
      (different-file-name test-file-name)
      (clear-data))

    (around [it]
      (with-redefs [data-type :json]
        (it)))

    (it "returns a vector of all the player names recorded in the file."
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 1}]
                    ["Sue" {:wins 1, :losses 0, :draws 1}]]
           names '("Sarah" "John" "Marge" "Sue")]
        (record-tally tally)
        (record-tally tally2)
        (should= names (player-names))))

    (it "returns player names if there is only one game set"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["Marge" {:wins 1, :losses 0, :draws 1}]]
            names '("Sarah" "Marge")]
        (record-tally tally)
        (should= names (player-names))))

    (it "only returns distinct names of multiple logged tallys"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["Sue" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Sue" {:wins 1, :losses 0, :draws 1}]
                   ["Marge" {:wins 1, :losses 1, :draws 1}]]
           names '("Sarah" "Sue" "Marge")]
        (record-tally tally1)
        (record-tally tally2)
        (should= names (player-names)))))

    (describe "read-total-tally"

      (before
        (different-file-name test-file-name)
        (clear-data))

      (around [it]
        (with-redefs [data-type :edn]
          (it)))

      (it "saves multiple tallys to a file and combines tallys of the same player name"
        (let [tally1 [["Sarah" {:wins 0, :losses 1, :draws 1}]
                     ["John" {:wins 1, :losses 0, :draws 1}]]
              tally2 [["Sarah" {:wins 1, :losses 1, :draws 0}]
                      ["John" {:wins 1, :losses 1, :draws 0}]]
             total-tally [["Sarah" {:wins 1, :losses 2, :draws 1}]
                          ["John" {:wins 2, :losses 1, :draws 1}]]]
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
          (record-tally tally1)
          (record-tally tally2)
          (should= total-tally (read-total-tally))))

      (it "returns the same set of data if there were no duplicate player names"
        (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                     ["John" {:wins 1, :losses 0, :draws 1}]]]
          (record-tally tally)
          (should= tally (read-total-tally))))))


(context "POSTGRES"

  (describe "player-names"

    (before
      (clear-data))

    (around [it]
      (with-redefs [data-type :pg]
        (it)))

    (it "returns a vector of all the player names recorded in the file."
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 1}]
                    ["Sue" {:wins 1, :losses 0, :draws 1}]]
           names '("Sarah" "John" "Marge" "Sue")]
        (record-tally tally)
        (record-tally tally2)
        (should= names (player-names))))

    (it "returns player names if there is only one game set"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["Marge" {:wins 1, :losses 0, :draws 1}]]
            names '("Sarah" "Marge")]
        (record-tally tally)
        (should= names (player-names))))

    (it "only returns distinct names of multiple logged tallys"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["Sue" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Sue" {:wins 1, :losses 0, :draws 1}]
                   ["Marge" {:wins 1, :losses 1, :draws 1}]]
           names '("Sarah" "Sue" "Marge")]
        (record-tally tally1)
        (record-tally tally2)
        (should= names (player-names)))))

    (describe "read-total-tally"

      (before
        (different-file-name test-file-name)
        (clear-data))

      (around [it]
        (with-redefs [data-type :edn]
          (it)))

      (it "saves multiple tallys to a file and combines tallys of the same player name"
        (let [tally1 [["Sarah" {:wins 0, :losses 1, :draws 1}]
                     ["John" {:wins 1, :losses 0, :draws 1}]]
              tally2 [["Sarah" {:wins 1, :losses 1, :draws 0}]
                      ["John" {:wins 1, :losses 1, :draws 0}]]
             total-tally [["Sarah" {:wins 1, :losses 2, :draws 1}]
                          ["John" {:wins 2, :losses 1, :draws 1}]]]
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
          (record-tally tally1)
          (record-tally tally2)
          (should= total-tally (read-total-tally))))

      (it "returns the same set of data if there were no duplicate player names"
        (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                     ["John" {:wins 1, :losses 0, :draws 1}]]]
          (record-tally tally)
          (should= tally (read-total-tally))))))
