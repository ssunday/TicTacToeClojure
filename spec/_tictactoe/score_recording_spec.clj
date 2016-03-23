(ns -tictactoe.ttt.score_recording-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.score_recording :refer :all]
            [-tictactoe.scoring.scoring_json :refer :all]
            [-tictactoe.ttt.score_reading :as read])
  (:use [-tictactoe.ttt.scoring_repository :only [alternate-file-name clear-all-data]]))

(def test-file-name "test")

(def test-data (->JSON))

(describe "record-tally"

  (before
    (alternate-file-name test-data test-file-name)
    (clear-all-data test-data))

  (it "saves tally set to a file which can be read off identically"
    (let [tally [["Sarah" {:wins 0, :losses 1, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]]
      (record-tally test-data tally)
      (should= tally (read/read-total-tally test-data)))))
