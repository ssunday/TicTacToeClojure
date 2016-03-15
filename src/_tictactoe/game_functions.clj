(ns -tictactoe.game_functions)

(defn make-default-board [dimension]
  (vec (range (* dimension dimension))))

(defn mark-board-location [board spot marker]
  (assoc board spot marker))

(defn winning-combinations [board]
  (cond (= (count board) 9) [(subvec board 0 3) (subvec board 3 6) (subvec board 6 9)
                              (into [] (list (nth board 0) (nth board 3) (nth board 6)))
                              (into [] (list (nth board 1) (nth board 4) (nth board 7)))
                              (into [] (list (nth board 2) (nth board 5) (nth board 8)))
                              (into [] (list (nth board 0) (nth board 4) (nth board 8)))
                              (into [] (list (nth board 2) (nth board 4) (nth board 6)))]
        (= (count board) 16) [(subvec board 0 4) (subvec board 4 8) (subvec board 8 12) (subvec board 12 16)
                              (into [] (list (nth board 0) (nth board 4) (nth board 8) (nth board 12)))
                              (into [] (list (nth board 1) (nth board 5) (nth board 9) (nth board 13)))
                              (into [] (list (nth board 2) (nth board 6) (nth board 10) (nth board 14)))
                              (into [] (list (nth board 3) (nth board 7) (nth board 11) (nth board 15)))
                              (into [] (list (nth board 0) (nth board 5) (nth board 10) (nth board 15)))
                              (into [] (list (nth board 3) (nth board 6) (nth board 9) (nth board 12)))]))

(defn get-whether-a-row-is-won [row]
  (= (count (distinct row)) 1))

(defn game-is-won [board]
  (first (first (filter #(get-whether-a-row-is-won %) (winning-combinations board)))))

(defn game-is-tied [board]
  (every? string? board))

(defn game-is-over [board]
  (or (game-is-tied board) (game-is-won board)))
