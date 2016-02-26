(ns -tictactoe.core)
(import java.util.LinkedList)

(defn get-spot-row
  [spot]
  (cond (< spot 3) 0
        (< spot 6) 1
        (< spot 9) 2))
(defn get-spot-row-location
  [spot]
  (mod spot 3))


(defn make-default-board []
  (let [v (vec (range 9))]
  [(subvec v 0 3) (subvec v 3 6) (subvec v 6 9)]))

(defn mark-board-location
  [board spot marker]
  (assoc-in board [(get-spot-row spot) (get-spot-row-location spot)] marker))

(defn -main []
  (println (make-default-board))
  (println "This is a main function."))
