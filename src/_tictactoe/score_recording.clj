(ns -tictactoe.score_recording
  (:use [clojure.java.io]))

(def player-scores-file-name "scores.txt")

(defn convert-string-to-number-if-number [str]
  (try
    (let [data (read-string str)]
       (if (number? data) data str))
    (catch Exception e nil)))

(defn record-scores [player-scores]
  (doseq [[player, score] player-scores]
    (spit player-scores-file-name (str player " " score "\n") :append true)))

(defn clear-file []
  (delete-file player-scores-file-name))

(defn read-all-scores []
  (let [scores (slurp player-scores-file-name)
        scores-split (re-seq #"\w+" scores)]
  (partition 2 (map #(convert-string-to-number-if-number %) scores-split))))
