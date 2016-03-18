;Responsible for validating input.

(ns -tictactoe.input_validation)

(def valid-dimensions ["3" "4"])

(defn convert-string-to-number [str]
  (try
    (let [spot (read-string str)]
       (if (number? spot) spot nil))
    (catch Exception e nil)))

(defn marker-is-invalid [marker]
  (or (> (count marker) 1)
      (clojure.string/blank? marker)
      (not (nil? (convert-string-to-number marker)))))

(defn first-player-marker-is-invalid [first-player-marker player-one-marker player-two-marker]
  (every? #(not= % first-player-marker) [player-one-marker player-two-marker]))

(defn spot-is-not-on-board [board spot]
  (nil? (find board spot)))

(defn spot-is-invalid-input [spot]
  (nil? spot))

(defn spot-is-already-marked [board spot]
  (string? (get board spot)))

(defn spot-is-invalid [board spot]
  (or (spot-is-not-on-board board spot)
      (spot-is-invalid-input spot)
      (spot-is-already-marked board spot)))

(defn invalid-board-dimension [dimension]
  (every? #(not= % dimension) valid-dimensions))

(defn y-or-n-response-is-invalid [response y n]
  (every? #(not= response %) [y n]))

(defn menu-option-is-valid [menu-length option]
  (and (number? option)
       (<= option menu-length)
       (> option 0)))
