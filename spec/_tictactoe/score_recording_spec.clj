(ns -tictactoe.score_recording-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.score_recording :refer :all]
            [-tictactoe.score_data_functions_for_testing :refer :all]
            [-tictactoe.score_reading :as read]))

(def test-file-name "test")

(describe "record-tally"

  (before
    (different-file-name test-file-name)
    (clear-data))

  (it "saves tally set to a file which can be read off identically"
    (let [tally [["Sarah" {:wins 0, :losses 1, :draws 1}]
                 ["John" {:wins 1, :losses 0, :draws 1}]]]
      (record-tally tally)
      (should= tally (read/read-total-tally)))))
