;Responsible for writing and reading from the EDN file type.

(ns -tictactoe.scoring.scoring_edn
  (:require [clojure.edn :as edn]
            [-tictactoe.ttt.scoring_repository :as repository]
            [-tictactoe.scoring.file_type_functions :as file]
            [clojure.java.io :refer :all]))

(def edn-file-name (atom "tally.edn"))

(defn- use-different-file-name [name]
  (reset! edn-file-name (str name ".edn")))

(defn- record-the-tallys [tallys]
  (doall (map #(spit @edn-file-name (prn-str %) :append true) tallys)))

(defn- read-the-tallys []
  (if (file/file-exists @edn-file-name)
    (with-open [rdr (reader @edn-file-name)]
        (map #(edn/read-string %) (doall (line-seq rdr))))))

(defrecord EDN [] repository/DataType
  (repository/alternate-file-name [this name] (use-different-file-name name))
  (repository/clear-all-data [this] (file/clear-file @edn-file-name))
  (repository/read-tally [this] (read-the-tallys))
  (repository/record-player-tallys [this tally] (record-the-tallys tally)))
