(ns -tictactoe.game_functions)

(defn make-default-board [dimension]
  (vec (range (* dimension dimension))))

(defn mark-board-location [board spot marker]
  (assoc board spot marker))

(defn game-is-tied [board]
  (every? string? board))

(defn get-whether-a-row-is-won [row]
  (= (count (distinct row)) 1))

(defn game-is-won-3x3 [board]
    (cond (get-whether-a-row-is-won (subvec board 0 3)) true
          (get-whether-a-row-is-won (subvec board 3 6)) true
          (get-whether-a-row-is-won (subvec board 6 9)) true
          (get-whether-a-row-is-won (list (nth board 0) (nth board 3) (nth board 6))) true
          (get-whether-a-row-is-won (list (nth board 1) (nth board 4) (nth board 7))) true
          (get-whether-a-row-is-won (list (nth board 2) (nth board 5) (nth board 8))) true
          (get-whether-a-row-is-won (list (nth board 0) (nth board 4) (nth board 8))) true
          (get-whether-a-row-is-won (list (nth board 2) (nth board 4) (nth board 6))) true
          :else false))

(defn game-is-won-4x4 [board]
    (cond (get-whether-a-row-is-won (subvec board 0 4)) true
          (get-whether-a-row-is-won (subvec board 4 8)) true
          (get-whether-a-row-is-won (subvec board 8 12)) true
          (get-whether-a-row-is-won (subvec board 12 16)) true
          (get-whether-a-row-is-won (list (nth board 0) (nth board 4) (nth board 8) (nth board 12))) true
          (get-whether-a-row-is-won (list (nth board 1) (nth board 5) (nth board 9) (nth board 13))) true
          (get-whether-a-row-is-won (list (nth board 2) (nth board 6) (nth board 10) (nth board 14))) true
          (get-whether-a-row-is-won (list (nth board 3) (nth board 7) (nth board 11) (nth board 15))) true
          (get-whether-a-row-is-won (list (nth board 0) (nth board 5) (nth board 10) (nth board 15))) true
          (get-whether-a-row-is-won (list (nth board 3) (nth board 6) (nth board 9) (nth board 12))) true
          :else false))

(defn game-is-won [board]
    (cond (= (count board) 9) (game-is-won-3x3 board)
          (= (count board) 16) (game-is-won-4x4 board)))

(defn game-is-won-or-tied [board]
  (or (game-is-tied board) (game-is-won board)))
