(ns -tictactoe.core-test
  (:require [clojure.test :refer :all]
            [-tictactoe.core :refer :all]))

(deftest make-default-board-test
  (testing "The default board is a nested vector of 0-8 grouped in threes."
    (is (= (make-default-board) [[0 1 2] [3 4 5] [6 7 8]]))))


(deftest mark-board-location-test
  (testing "Mark board location correctly marks given board given spot and marker."
    (is (= (mark-board-location (make-default-board) 3 "X") [[0 1 2] ["X" 4 5] [6 7 8]]))))

(deftest get-spot-row-test
  (testing "Get spot row returns 0 for spot value of 0-2, 1 for 3-5, 2 for 6-8."
    (is (= (get-spot-row 0) 0))
    (is (= (get-spot-row 1) 0))
    (is (= (get-spot-row 2) 0))
    (is (= (get-spot-row 3) 1))
    (is (= (get-spot-row 4) 1))
    (is (= (get-spot-row 5) 1))
    (is (= (get-spot-row 6) 2))
    (is (= (get-spot-row 7) 2))
    (is (= (get-spot-row 8) 2))
    ))

(deftest get-spot-row-location-test
  (testing "Get spot row location gives index location of spot within row."
    (is (= (get-spot-row-location 0) 0))
    (is (= (get-spot-row-location 1) 1))
    (is (= (get-spot-row-location 2) 2))
    (is (= (get-spot-row-location 3) 0))
    (is (= (get-spot-row-location 4) 1))
    (is (= (get-spot-row-location 5) 2))
    (is (= (get-spot-row-location 6) 0))
    (is (= (get-spot-row-location 7) 1))
    (is (= (get-spot-row-location 8) 2))
  ))
