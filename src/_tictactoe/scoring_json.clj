;Responsible for writing and reading from the JSON file type.

(ns -tictactoe.scoring_json
  (:require [cheshire.core :as json]
            [-tictactoe.scoring_repository :as repository]
            [-tictactoe.file_type_functions :as file])
  (:use [clojure.java.io]))

(def json-file-name "tally.json")

(defn- read-the-tallys []
  (if (file/file-exists json-file-name)
    (with-open [rdr (reader json-file-name)]
      (vec (map #(vec (json/parse-string % true)) (doall (line-seq rdr)))))))

(defn- record-the-tallys [tallys]
  (doall (map #(spit json-file-name (str (json/generate-string %) "\n") :append true) tallys)))

(defn- use-different-file-name [name]
  (def json-file-name (str name ".json")))

(defrecord JSON [] repository/DataType
  (repository/alternate-file-name [this name] (use-different-file-name name))
  (repository/clear-all-data [this] (file/clear-file json-file-name))
  (repository/read-tally [this] (read-the-tallys))
  (repository/record-player-tallys [this tally] (record-the-tallys tally)))
