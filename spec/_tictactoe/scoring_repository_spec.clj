(ns -tictactoe.scoring_repository-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.scoring_repository :refer :all]
            [-tictactoe.scoring_json :refer :all]
            [-tictactoe.scoring_edn :refer :all]
            [-tictactoe.scoring_postgres :refer :all]))

(def test-file-name "test")

(context "JSON"

  (def json-type (->JSON))

  (before
    (alternate-file-name json-type test-file-name)
    (clear-all-data json-type))

  (describe "read-tally"

    (it "returns nil for an empty json file"
      (should= nil (read-tally json-type)))

    (it "can retrieve multiple tallys as a vector"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]]
        (record-player-tallys json-type tally)
        (should= tally (read-tally json-type))))

    (it "returns multiple sets of tallys as a vector with all of them on the same level"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 1, :losses 1, :draws 1}]
                   ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys json-type tally1)
        (record-player-tallys json-type tally2)
        (should= (into [] (concat tally1 tally2)) (read-tally json-type)))))

  (describe "record-player-tallys"

    (it "saves a tally to an empty file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys json-type tally)
        (should= 1 (count (line-seq (clojure.java.io/reader @json-file-name))))))

    (it "saves a set of tallys to an empty file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys json-type tally)
        (should= 2 (count (line-seq (clojure.java.io/reader @json-file-name))))))

    (it "appends a set of tallys to an already populated file"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                    ["John" {:wins 0, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 2}]
                    ["Beth" {:wins 0, :losses 0, :draws 2}]]]
        (record-player-tallys json-type tally1)
        (record-player-tallys json-type tally2)
        (should= 4 (count (line-seq (clojure.java.io/reader @json-file-name))))))))

(context "EDN"

  (def edn-type (->EDN))

  (before
    (alternate-file-name edn-type test-file-name)
    (clear-all-data edn-type))

  (describe "read-tally"

    (it "returns nil for an empty edn file"
      (should= nil (read-tally edn-type)))

    (it "can retrieve multiple tallys as a vector"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]]
        (record-player-tallys edn-type tally)
        (should= tally (read-tally edn-type))))

    (it "returns multiple sets of tallys as a vector with all of them on the same level"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 1, :losses 1, :draws 1}]
                   ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys edn-type tally1)
        (record-player-tallys edn-type tally2)
        (should= (into [] (concat tally1 tally2)) (read-tally edn-type)))))

  (describe "record-player-tallys"

    (it "saves a single tally to an empty edn file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys edn-type tally)
        (should= 1 (count (line-seq (clojure.java.io/reader @edn-file-name))))))

    (it "saves a set of tallys to an empty edn file"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys edn-type tally)
        (should= 2 (count (line-seq (clojure.java.io/reader @edn-file-name))))))

    (it "appends a set of tallys to an already populated edn file"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                    ["John" {:wins 0, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 2}]
                    ["Beth" {:wins 0, :losses 0, :draws 2}]]]
        (record-player-tallys edn-type tally1)
        (record-player-tallys edn-type tally2)
        (should= 4 (count (line-seq (clojure.java.io/reader @edn-file-name))))))))

(context "POSTGRES"

  (def pg-type (->POSTGRES))

  (before
    (clear-all-data pg-type))

  (after-all
    (clear-all-data pg-type))

  (describe "record-player-tallys"

    (it "saves a set of tallys to an empty database"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys pg-type tally)
        (should= 2 (count (read-tally pg-type)))))

    (it "appends a set of tallys to an already populated database"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                    ["John" {:wins 0, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 0, :losses 0, :draws 2}]
                    ["Beth" {:wins 0, :losses 0, :draws 2}]]]
        (record-player-tallys pg-type tally1)
        (record-player-tallys pg-type tally2)
        (should= 4 (count (read-tally pg-type))))))

  (describe "read-tally"

    (it "returns nil for an empty postgres table"
      (should= nil (read-tally pg-type)))

    (it "can retrieve multiple tallys as a vector"
      (let [tally [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]]
        (record-player-tallys pg-type tally)
        (should= tally (read-tally pg-type))))

    (it "returns multiple sets of tallys as a vector with all of them on the same level"
      (let [tally1 [["Sarah" {:wins 0, :losses 0, :draws 1}]
                   ["John" {:wins 1, :losses 0, :draws 1}]]
            tally2 [["Marge" {:wins 1, :losses 1, :draws 1}]
                   ["Sarah" {:wins 0, :losses 0, :draws 1}]]]
        (record-player-tallys pg-type tally1)
        (record-player-tallys pg-type tally2)
        (should= (into [] (concat tally1 tally2)) (read-tally pg-type))))))
