(ns -tictactoe.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_recording :refer :all]))


(describe "record-scores"
  (it "saves scores to file"
    (let [scores {"sarah" 1 "bob" 2}]
    (clear-file)
    (record-scores scores)
    (should= scores (read-all-scores))))
  (it "saves multiple scores to file"
    (let [score1 {"sarah" 1 "bob" 2}
          score2 {"Fred" 0 "George" 0}]
    (clear-file)
    (record-scores score1)
    (record-scores score2)
    (should= (merge score2 score1) (read-all-scores)))))
