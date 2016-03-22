;Responsible for writing and reading from the EDN file type.

(ns -tictactoe.scoring_edn
  (:require [clojure.edn :as edn]
            [-tictactoe.scoring_repository :as repository]
            [-tictactoe.file_type_functions :as file])
  (:use [clojure.java.io]))

(def edn-file-name "tally.edn")

(defn- use-different-file-name [name]
  (def edn-file-name (str name ".edn")))

(defn- record-the-tallys [tallys]
  (doall (map #(spit edn-file-name (prn-str %) :append true) tallys)))

(defn- read-the-tallys []
  (if (file/file-exists edn-file-name)
    (with-open [rdr (reader edn-file-name)]
        (map #(edn/read-string %) (doall (line-seq rdr))))))

(defrecord EDN [] repository/DataType
  (repository/alternate-file-name [this name] (use-different-file-name name))
  (repository/clear-all-data [this] (file/clear-file edn-file-name))
  (repository/read-tally [this] (read-the-tallys))
  (repository/record-player-tallys [this tally] (record-the-tallys tally)))
