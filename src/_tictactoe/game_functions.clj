(ns -tictactoe.game_functions)

(defn game-is-tied
  [board]
  (every? string? board))

(defn game-is-won
  [board]
    (cond (= (count (distinct (subvec board 0 3))) 1) true
          (= (count (distinct (subvec board 3 6))) 1) true
          (= (count (distinct (subvec board 6 9))) 1) true
          (= (count (distinct (list (nth board 0) (nth board 3) (nth board 6)))) 1) true
          (= (count (distinct (list (nth board 1) (nth board 4) (nth board 7)))) 1) true
          (= (count (distinct (list (nth board 2) (nth board 5) (nth board 8)))) 1) true
          (= (count (distinct (list (nth board 0) (nth board 4) (nth board 8)))) 1) true
          (= (count (distinct (list (nth board 2) (nth board 4) (nth board 6)))) 1) true
          :else false))

(defn make-default-board []
  (vec (range 9)))

(defn mark-board-location
  [board spot marker]
  (assoc board spot marker))
