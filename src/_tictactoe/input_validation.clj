(ns -tictactoe.input_validation)

(defn convert-string-to-number [str]
  (try
    (let [spot (read-string str)]
       (if (number? spot) spot nil))
    (catch Exception e nil)))

(defn check-if-marker-is-invalid [marker]
  (or (> (count marker) 1)
      (clojure.string/blank? marker)
      (not (nil? (convert-string-to-number marker)))))

(defn check-if-first-player-marker-is-invalid [first-player-marker player-one-marker player-two-marker]
  (every? #(not= % first-player-marker) [player-one-marker player-two-marker]))

(defn check-if-spot-is-invalid-input [board spot]
  (nil? spot))

(defn check-if-spot-is-already-marked [board spot]
  (string? (get board spot)))

(defn check-if-spot-is-not-on-board [board spot]
  (nil? (find board spot)))

(defn check-if-spot-is-invalid [board spot]
  (or (nil? (find board spot))
      (not  (number? (get board spot)))))

(defn check-if-yes-or-no-response-is-invalid [response y n]
  (every? #(not= response %) [y n]))

(defn check-if-menu-option-is-valid [menu-options option]
  (and (number? option)
       (<= option (count menu-options))
       (> option 0)))
