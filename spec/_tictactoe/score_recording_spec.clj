(ns -tictactoe.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_recording :refer :all]))


(describe "record-scores"
  (it "saves scores to file"
    (let [scores {"sarah" 1 "bob" 2}]
    (clear-file)
    (record-scores scores)
    (should= (lazy-seq scores) (read-all-scores))))

  (it "saves two scores to file"
    (let [score1 {"sarah" 1 "bob" 2}
          score2 {"Fred" 0 "George" 0}]
    (clear-file)
    (record-scores score1)
    (record-scores score2)
    (should= (lazy-seq (merge score1 score2)) (read-all-scores))))

  (it "saves two scores to file"
    (let [score1 {"sarah" 1 "bob" 2}
          score2 {"Fred" 0 "George" 0}
          score3 {"bobby" 1 "sammy" 2}]
    (clear-file)
    (record-scores score1)
    (record-scores score2)
    (record-scores score3)
    (should= (lazy-seq (merge score1 score2 score3)) (read-all-scores)))))
