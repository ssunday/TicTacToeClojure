(ns -tictactoe.web.board_presenter-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.web.board_presenter :refer :all]))

(describe "parse-board-for-display"
  (context "3x3"
    (it "creates a hash for all spots with all open values and number values when board is empty and respective :start-row/:end-row values"
      (let [board [0 1 2 3 4 5 6 7 8]]
        (should= [{:open true :value 0 :start-row true :end-row false}
                  {:open true :value 1 :start-row false :end-row false}
                  {:open true :value 2 :start-row false :end-row true}
                  {:open true :value 3 :start-row true :end-row false}
                  {:open true :value 4 :start-row false :end-row false}
                  {:open true :value 5 :start-row false :end-row true}
                  {:open true :value 6 :start-row true :end-row false}
                  {:open true :value 7 :start-row false :end-row false}
                  {:open true :value 8 :start-row false :end-row true}] (parse-board-for-display board))))

    (it "creates a hash for all spots with one false open values when a spot is marked"
      (let [board [0 1 2 3 "X" 5 6 7 8]]
        (should= [{:open true :value 0 :start-row true  :end-row false}
                  {:open true :value 1 :start-row false :end-row false}
                  {:open true :value 2 :start-row false :end-row true}
                  {:open true :value 3 :start-row true :end-row false}
                  {:open false :value "X" :start-row false :end-row false}
                  {:open true :value 5 :start-row false :end-row true}
                  {:open true :value 6 :start-row true :end-row false}
                  {:open true :value 7 :start-row false :end-row false}
                  {:open true :value 8 :start-row false :end-row true}] (parse-board-for-display board))))

    (it "creates a hash for all spots with some false open values when board is partially marked"
      (let [board [0 1 "O" 3 "X" 5 6 "X" 8]]
        (should= [{:open true :value 0 :start-row true  :end-row false}
                  {:open true :value 1 :start-row false :end-row false}
                  {:open false :value "O" :start-row false :end-row true}
                  {:open true :value 3 :start-row true :end-row false}
                  {:open false :value "X" :start-row false :end-row false}
                  {:open true :value 5 :start-row false :end-row true}
                  {:open true :value 6 :start-row true :end-row false}
                  {:open false :value "X" :start-row false :end-row false}
                  {:open true :value 8 :start-row false :end-row true}] (parse-board-for-display board)))))

  (context "4x4"
    (it "creates a hash for all spots with all open values and number values when board is empty and respective :start-row/:end-row values"
      (let [board [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]]
        (should= [{:open true :value 0 :start-row true :end-row false}
                  {:open true :value 1 :start-row false :end-row false}
                  {:open true :value 2 :start-row false :end-row false}
                  {:open true :value 3 :start-row false :end-row true}
                  {:open true :value 4 :start-row true :end-row false}
                  {:open true :value 5 :start-row false :end-row false}
                  {:open true :value 6 :start-row false :end-row false}
                  {:open true :value 7 :start-row false :end-row true}
                  {:open true :value 8 :start-row true :end-row false}
                  {:open true :value 9 :start-row false :end-row false}
                  {:open true :value 10 :start-row false :end-row false}
                  {:open true :value 11 :start-row false :end-row true}
                  {:open true :value 12 :start-row true :end-row false}
                  {:open true :value 13 :start-row false :end-row false}
                  {:open true :value 14 :start-row false :end-row false}
                  {:open true :value 15 :start-row false :end-row true}] (parse-board-for-display board))))

    (it "creates a hash for all spots with one false open values when a spot is marked"
      (let [board [0 1 2 3 4 "X" 6 7 8 9 10 11 12 13 14 15]]
        (should= [{:open true :value 0 :start-row true :end-row false}
                  {:open true :value 1 :start-row false :end-row false}
                  {:open true :value 2 :start-row false :end-row false}
                  {:open true :value 3 :start-row false :end-row true}
                  {:open true :value 4 :start-row true :end-row false}
                  {:open false :value "X" :start-row false :end-row false}
                  {:open true :value 6 :start-row false :end-row false}
                  {:open true :value 7 :start-row false :end-row true}
                  {:open true :value 8 :start-row true :end-row false}
                  {:open true :value 9 :start-row false :end-row false}
                  {:open true :value 10 :start-row false :end-row false}
                  {:open true :value 11 :start-row false :end-row true}
                  {:open true :value 12 :start-row true :end-row false}
                  {:open true :value 13 :start-row false :end-row false}
                  {:open true :value 14 :start-row false :end-row false}
                  {:open true :value 15 :start-row false :end-row true}] (parse-board-for-display board))))

    (it "creates a hash for all spots with some false open values when board is partially marked"
      (let [board ["O" 1 2 3 4 "X" 6 7 "O" 9 10 11 "X" 13 14 15]]
        (should= [{:open false :value "O" :start-row true :end-row false}
                  {:open true :value 1 :start-row false :end-row false}
                  {:open true :value 2 :start-row false :end-row false}
                  {:open true :value 3 :start-row false :end-row true}
                  {:open true :value 4 :start-row true :end-row false}
                  {:open false :value "X" :start-row false :end-row false}
                  {:open true :value 6 :start-row false :end-row false}
                  {:open true :value 7 :start-row false :end-row true}
                  {:open false :value "O" :start-row true :end-row false}
                  {:open true :value 9 :start-row false :end-row false}
                  {:open true :value 10 :start-row false :end-row false}
                  {:open true :value 11 :start-row false :end-row true}
                  {:open false :value "X" :start-row true :end-row false}
                  {:open true :value 13 :start-row false :end-row false}
                  {:open true :value 14 :start-row false :end-row false}
                  {:open true :value 15 :start-row false :end-row true}] (parse-board-for-display board)))))


)
