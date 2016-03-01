(ns -tictactoe.game_functions)

(defn make-default-board []
  (vec (range 9)))

(defn mark-board-location [board spot marker]
  (assoc board spot marker))

(defn game-is-tied [board]
  (every? string? board))

(defn get-whether-three-in-a-row-is-won [row]
  (= (count (distinct row)) 1))

(defn game-is-won [board]
    (cond (get-whether-three-in-a-row-is-won (subvec board 0 3)) true
          (get-whether-three-in-a-row-is-won (subvec board 3 6)) true
          (get-whether-three-in-a-row-is-won (subvec board 6 9)) true
          (get-whether-three-in-a-row-is-won (list (nth board 0) (nth board 3) (nth board 6))) true
          (get-whether-three-in-a-row-is-won (list (nth board 1) (nth board 4) (nth board 7))) true
          (get-whether-three-in-a-row-is-won (list (nth board 2) (nth board 5) (nth board 8))) true
          (get-whether-three-in-a-row-is-won (list (nth board 0) (nth board 4) (nth board 8))) true
          (get-whether-three-in-a-row-is-won (list (nth board 2) (nth board 4) (nth board 6))) true
          :else false))

(defn game-is-won-or-tied [board]
  (or (game-is-tied board) (game-is-won board)))
