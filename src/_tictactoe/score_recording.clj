(ns -tictactoe.score_recording
  (:require [clojure.edn :as edn])
  (:use [clojure.java.io]))

(def player-tally-file-name "tally.edn")

(defn record-tally [player-tally]
  (doseq [player player-tally]
    (spit player-tally-file-name (prn-str player) :append true)))

(defn clear-file []
  (delete-file player-tally-file-name))

(defn read-tally []
  (if (.exists (file player-tally-file-name))
    (with-open [rdr (reader player-tally-file-name)]
      (map #(edn/read-string %) (doall (line-seq rdr))))
    nil))

(defn player-names []
  (let [tally (read-tally)]
    (vec (distinct (map #(first %) tally)))))

(defn read-total-tally []
  (let [tally (read-tally)
        freq (frequencies tally)
        total-tally (atom (zipmap (player-names) (repeat {:wins 0 :losses 0 :draws 0})))]
    (doseq [tally-set tally]
      (swap! total-tally update-in [(first tally-set) :wins] + ((second tally-set) :wins))
      (swap! total-tally update-in [(first tally-set) :losses] + ((second tally-set) :losses))
      (swap! total-tally update-in [(first tally-set) :draws] + ((second tally-set) :draws)))
    @total-tally))
