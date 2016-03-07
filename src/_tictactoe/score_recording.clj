(ns -tictactoe.score_recording
  (:use [clojure.java.io]))

(def player-scores-file-name "scores.txt")

(defn record-scores [player-scores]
  (doseq [[player, score] player-scores]
    (spit player-scores-file-name (str player " " score "\n") :append true)))

(defn clear-file []
  (delete-file player-scores-file-name))

(defn read-all-scores []
  (let [scores (slurp player-scores-file-name)
        scores-split (re-seq #"\w+" scores)
        hashed-scores (apply hash-map scores-split)]
  (into {} (for [[name score] hashed-scores] [name (read-string score)]))))
