(ns -tictactoe.web.scores_presenter-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.scoring_schema :as schema]
            [-tictactoe.web.scores_presenter :refer :all]))

(describe "display-scores"
  (it "returns a string containing tally header"
    (let [tally-header "W/L/D"
          name-header "Player"
          tallys [["Sarah" {schema/wins 0 schema/losses 0 schema/draws 1}]
                  ["John" {schema/wins 0 schema/losses 0 schema/draws 1}]]]
      (should (clojure.string/includes? (display-scores tallys name-header tally-header) tally-header))))

(it "returns a string containing player name"
  (let [tally-header "W/L/D"
        name-header "Player"
        tallys [["Sarah" {schema/wins 0 schema/losses 0 schema/draws 1}]
                ["John" {schema/wins 0 schema/losses 0 schema/draws 1}]]]
    (should (clojure.string/includes? (display-scores tallys name-header tally-header) (first (first tallys)))))))
