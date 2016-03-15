(ns -tictactoe.score_recording
  (:require [cheshire.core :as json]
            [-tictactoe.scoring_schema :as schema])
  (:use [clojure.java.io]))

(def player-tally-file-name "tally.json")

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
      (vec (map #(vec (json/parse-string % true)) (doall (line-seq rdr)))))))

(defn player-names []
  (let [player-tally (read-tally)]
    (distinct (map #(first %) player-tally))))

(defn read-total-tally []
  (let [tally (read-tally)
        total-tally (atom (zipmap (player-names) (repeat schema/default-wins-losses-draws-scores)))]
    (doseq [tally-set tally]
      (doall (map #(swap! total-tally update-in [(first tally-set) %] + ((second tally-set) %)) schema/wins-losses-draws)))
    (vec @total-tally)))
