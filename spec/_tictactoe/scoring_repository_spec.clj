(ns -tictactoe.scoring_repository-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.scoring_repository :refer :all]
            [-tictactoe.scoring_json :refer :all]
            [-tictactoe.scoring_edn :refer :all]
            [-tictactoe.scoring_postgres :refer :all]))

(def test-file-name "test")

(context "JSON"

  (def json-type (keyword "json"))

  (before
    (alternate-file-name {:file-type json-type  :file-name test-file-name})
    (clear-all-data json-type))

  (describe "read-tally"

    (it "returns nil for an empty json file"
      (should= nil (read-tally json-type )))

    (it "can retrieve multiple tallys as a vector"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally :data-type json-type})
        (should= tally (read-tally json-type))))

    (it "returns multiple sets of tallys as a vector with all of them on the same level"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 1, :losses 1, :draws 1}]
                   ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally1 :data-type json-type})
        (record-player-tallys {:tally tally2 :data-type json-type})
        (should= (into [] (concat tally1 tally2)) (read-tally json-type)))))

  (before
    (alternate-file-name {:file-type json-type :file-name test-file-name})
    (clear-all-data json-type))

  (describe "record-player-tallys"

    (it "saves a tally to an empty file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally :data-type json-type})
        (should= 1 (count (line-seq (clojure.java.io/reader json-file-name))))))

    (it "saves a set of tallys to an empty file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally :data-type json-type})
        (should= 2 (count (line-seq (clojure.java.io/reader json-file-name))))))

    (it "appends a set of tallys to an already populated file"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                    ["John" {:wins 0, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 2}]
                    ["Beth" {:wins 0, :losses 0, :draws 2}]]]
        (record-player-tallys {:tally tally1 :data-type json-type})
        (record-player-tallys {:tally tally2 :data-type json-type})
        (should= 4 (count (line-seq (clojure.java.io/reader json-file-name))))))))

(context "EDN"

  (def edn-type (keyword "edn"))

  (before
    (alternate-file-name {:file-type edn-type :file-name test-file-name})
    (clear-all-data edn-type))

  (describe "read-tally"

    (it "returns nil for an empty edn file"
      (alternate-file-name {:file-type edn-type :file-name test-file-name})
      (clear-all-data edn-type)
      (should= nil (read-tally edn-type)))

    (it "can retrieve multiple tallys as a vector"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]]
        (alternate-file-name {:file-type edn-type :file-name test-file-name})
        (clear-all-data edn-type)
        (record-player-tallys {:tally tally :data-type edn-type})
        (should= tally (read-tally edn-type))))

    (it "returns multiple sets of tallys as a vector with all of them on the same level"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 1, :losses 1, :draws 1}]
                   ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (alternate-file-name {:file-type edn-type :file-name test-file-name})
        (clear-all-data edn-type)
        (record-player-tallys {:tally tally1 :data-type edn-type})
        (record-player-tallys {:tally tally2 :data-type edn-type})
        (should= (into [] (concat tally1 tally2)) (read-tally edn-type)))))

  (before
    (alternate-file-name {:file-type edn-type :file-name test-file-name})
    (clear-all-data edn-type))

  (describe "record-player-tallys"

    (it "saves a single tally to an empty edn file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally :data-type edn-type})
        (should= 1 (count (line-seq (clojure.java.io/reader edn-file-name))))))

    (it "saves a set of tallys to an empty edn file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally :data-type edn-type})
        (should= 2 (count (line-seq (clojure.java.io/reader edn-file-name))))))

    (it "appends a set of tallys to an already populated edn file"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                    ["John" {:wins 0, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 2}]
                    ["Beth" {:wins 0, :losses 0, :draws 2}]]]
        (record-player-tallys {:tally tally1 :data-type edn-type})
        (record-player-tallys {:tally tally2 :data-type edn-type})
        (should= 4 (count (line-seq (clojure.java.io/reader edn-file-name))))))))

(context "POSTGRES"

  (def pg-type (keyword "pg"))

  (before
    (clear-all-data pg-type))

  (after-all
    (clear-all-data pg-type))

  (describe "record-player-tallys"

    (it "saves a set of tallys to an empty database"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally :data-type pg-type})
        (should= 2 (count (read-tally pg-type)))))

    (it "appends a set of tallys to an already populated database"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                    ["John" {:wins 0, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 2}]
                    ["Beth" {:wins 0, :losses 0, :draws 2}]]]
        (record-player-tallys {:tally tally1 :data-type pg-type})
        (record-player-tallys {:tally tally2 :data-type pg-type})
        (should= 4 (count (read-tally pg-type))))))

  (describe "read-tally"

    (it "returns nil for an empty postgres table"
      (should= nil (read-tally pg-type)))

    (it "can retrieve multiple tallys as a vector"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally :data-type pg-type})
        (should= tally (read-tally pg-type))))

    (it "returns multiple sets of tallys as a vector with all of them on the same level"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 1, :losses 1, :draws 1}]
                   ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys {:tally tally1 :data-type pg-type})
        (record-player-tallys {:tally tally2 :data-type pg-type})
        (should= (into [] (concat tally1 tally2)) (read-tally pg-type))))))
