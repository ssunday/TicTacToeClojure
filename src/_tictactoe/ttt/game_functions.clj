;Responsible for the game board state, i.e whether the game has been won, marking the board, and so on.

(ns -tictactoe.ttt.game_functions)

(defn make-default-board [dimension]
  (-> (* dimension dimension)
      range
      vec))

(defn mark-board-location [board spot marker]
  (assoc board spot marker))

(defmulti winning-combinations (fn [board] (count board)))

(defmethod winning-combinations 9 [board]
  [(subvec board 0 3) (subvec board 3 6) (subvec board 6 9)
   (into [] (list (nth board 0) (nth board 3) (nth board 6)))
   (into [] (list (nth board 1) (nth board 4) (nth board 7)))
   (into [] (list (nth board 2) (nth board 5) (nth board 8)))
   (into [] (list (nth board 0) (nth board 4) (nth board 8)))
   (into [] (list (nth board 2) (nth board 4) (nth board 6)))])

(defmethod winning-combinations 16 [board]
  [(subvec board 0 4) (subvec board 4 8) (subvec board 8 12) (subvec board 12 16)
   (into [] (list (nth board 0) (nth board 4) (nth board 8) (nth board 12)))
   (into [] (list (nth board 1) (nth board 5) (nth board 9) (nth board 13)))
   (into [] (list (nth board 2) (nth board 6) (nth board 10) (nth board 14)))
   (into [] (list (nth board 3) (nth board 7) (nth board 11) (nth board 15)))
   (into [] (list (nth board 0) (nth board 5) (nth board 10) (nth board 15)))
   (into [] (list (nth board 3) (nth board 6) (nth board 9) (nth board 12)))])

(defn- get-whether-a-row-is-won [row]
  (= (count (distinct row)) 1))

(defn game-is-won [board]
  (first (first (filter #(get-whether-a-row-is-won %) (winning-combinations board)))))

(defn game-is-tied [board]
  (every? string? board))

(defn game-is-over [board]
  (or (game-is-tied board) (game-is-won board)))
