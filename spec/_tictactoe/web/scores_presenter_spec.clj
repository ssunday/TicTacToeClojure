(ns -tictactoe.web.scores_presenter-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.scoring_schema :as schema]
            [-tictactoe.web.scores_presenter :refer :all]))

(describe "display-scores"
  (it "returns a string of html formatted player tallys"
    (let [tally-header "W/L/D"
          tallys [["Sarah" {schema/wins 0 schema/losses 0 schema/draws 1}]
                  ["John" {schema/wins 0 schema/losses 0 schema/draws 1}]]]
      (should= (str "Sarah:<br>" tally-header "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    (schema/wins (second (first tallys))) "/" (schema/losses (second (first tallys))) "/" (schema/draws (second (first tallys)))
                    "<br><br>"
                    "John:<br>" tally-header "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    (schema/wins (second (second tallys))) "/" (schema/losses (second (second tallys))) "/" (schema/draws (second (second tallys)))
                    "<br><br>")
               (display-scores tallys tally-header)))))
