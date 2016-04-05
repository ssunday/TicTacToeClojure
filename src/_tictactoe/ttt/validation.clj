(ns -tictactoe.ttt.validation)

(def valid-dimensions [3 4])

(defn invalid-board-dimension [dimension]
  (every? #(not= % dimension) valid-dimensions))

(defn marker-is-valid [marker]
  (re-matches #"[A-Z]" marker))

(defn first-player-invalid [first-player player-one player-two]
  (every? #(not= % first-player) [player-one player-two]))

(defn spot-is-valid-location [board spot]
  (and (number? spot)
       (number? (get board spot))))

(defn name-exists [name]
  (not (clojure.string/blank? name)))
