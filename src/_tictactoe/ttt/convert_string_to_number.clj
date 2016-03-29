;Responsible for converting a string to an integer.

(ns -tictactoe.ttt.convert_string_to_number)

(defn convert-string-to-number [str]
  (try
    (let [spot (read-string str)]
       (if (number? spot) spot nil))
  (catch Exception e nil)))
