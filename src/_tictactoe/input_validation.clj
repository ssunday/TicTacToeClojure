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
  (and (not (= first-player-marker player-one-marker))
       (not (= first-player-marker player-two-marker))))

(defn check-if-spot-is-not-open [board spot]
  (or (nil? (find board spot))
          (not  (number? (get board spot)))))
