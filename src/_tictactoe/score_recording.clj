(ns -tictactoe.score_recording
  (:require [cheshire.core :as json])
  (:use [clojure.java.io]))

(def player-tally-file-name "tally.json")

(def win-keyword (keyword "wins"))

(def losses-keyword (keyword "losses"))

(def draws-keyword (keyword "draws"))

(defn alternate-file-name [file-name]
  (def player-tally-file-name file-name))

(defn file-exists? []
  (.exists (file player-tally-file-name)))

(defn clear-file []
  (if (file-exists?)
    (delete-file player-tally-file-name)))

(defn record-tally [player-tally]
  (doall (map #(spit player-tally-file-name (str (json/generate-string %) "\n") :append true) player-tally)))

(defn read-tally []
  (if (file-exists?)
    (with-open [rdr (reader player-tally-file-name)]
      (vec (map #(vec (json/parse-string % true)) (doall (line-seq rdr)))))
    nil))

(defn player-names []
  (let [player-tally (read-tally)]
    (distinct (map #(first %) player-tally))))

(defn read-total-tally []
  (let [tally (read-tally)
        total-tally (atom (zipmap (player-names) (repeat {win-keyword 0 losses-keyword 0 draws-keyword 0})))
        wins-losses-draws [win-keyword losses-keyword draws-keyword]]
    (doseq [tally-set tally]
      (doall (map #(swap! total-tally update-in [(first tally-set) %] + ((second tally-set) %)) wins-losses-draws)))
    (vec @total-tally)))
