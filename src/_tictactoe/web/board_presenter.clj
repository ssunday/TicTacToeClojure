(ns -tictactoe.web.board_presenter)

(defn- spot-is-open [spot]
  (number? spot))

(defn- row-starters [board]
  (let [dimension (-> board count Math/sqrt int)]
    (map #(zero? (mod % dimension)) (range (count board)))))

(defn- row-enders [board]
  (let [board-length (count board)
        dimension (int (Math/sqrt board-length))]
    (map #(= (mod % dimension) (dec dimension)) (range board-length))))

(defn- build-open-value-row-list-for-each-spot [board]
  (let [spot-values-and-whether-open (map #(list (spot-is-open %) %) board)
        which-spots-begin-rows (row-starters board)
        which-spots-end-rows (row-enders board)]
    (map #(list (first %1) (second %1) %2 %3) spot-values-and-whether-open which-spots-begin-rows which-spots-end-rows)))

(defn parse-board-for-display [board]
  (let [open-value-row-list (build-open-value-row-list-for-each-spot board)]
     (vec (map #(zipmap [:open :value :start-row :end-row] %) open-value-row-list))))
