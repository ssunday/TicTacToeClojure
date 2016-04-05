(ns -tictactoe.web.scores_presenter-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.scoring_schema :as schema]
            [-tictactoe.web.scores_presenter :refer :all]))

(describe "get-parsed-scores"
  (it "vector containing a hash of the first player name and string W/L/D score with key value of that name"
    (let [tallys [["Sarah" {schema/wins 0 schema/losses 0 schema/draws 1}]
                  ["John" {schema/wins 0 schema/losses 0 schema/draws 1}]]]
      (should (some #(= {:name "Sarah" :score "0/0/1"} %) (get-parsed-scores tallys)))))

  (it "vector containing a hash of the second player name and string W/L/D score with key value of that name"
    (let [tallys [["Sarah" {schema/wins 0 schema/losses 0 schema/draws 1}]
                  ["John" {schema/wins 0 schema/losses 0 schema/draws 1}]]]
      (should (some #(= {:name "John" :score "0/0/1"} %) (get-parsed-scores tallys))))))
