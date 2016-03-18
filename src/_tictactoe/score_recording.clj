;Responsible for game scoring and tallying.

(ns -tictactoe.score_recording
  (:require [cheshire.core :as json]
            [-tictactoe.scoring_schema :as schema]
            [-tictactoe.scoring_repository :as repository])
  (:use [clojure.java.io]))

(def data-type (or (keyword (System/getenv "TYPE"))
                    :json))

(defn different-file-name [file-name]
  (repository/alternate-file-name {:file-type data-type :file-name file-name}))

(defn clear-data []
  (repository/clear-all-data data-type))

(defn record-tally [player-tally]
  (repository/record-player-tallys {:data-type data-type :tally player-tally}))

(defn player-names []
  (let [player-tally (repository/read-tally data-type)]
    (distinct (map #(first %) player-tally))))

(defn read-total-tally []
  (let [tally (repository/read-tally data-type)
        total-tally (atom (zipmap (player-names) (repeat schema/default-wins-losses-draws-scores)))]
    (doseq [tally-set tally]
      (doall (map #(swap! total-tally update-in [(first tally-set) %] + ((second tally-set) %)) schema/wins-losses-draws)))
    (vec @total-tally)))
