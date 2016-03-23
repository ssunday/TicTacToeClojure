;Responsible for writing and reading from the JSON file type.

(ns -tictactoe.scoring.scoring_json
  (:require [cheshire.core :as json]
            [-tictactoe.ttt.scoring_repository :as repository]
            [-tictactoe.scoring.file_type_functions :as file]
            [clojure.java.io :refer :all]))

(def json-file-name (atom "tally.json"))

(defn- use-different-file-name [name]
  (reset! json-file-name (str name ".json")))

(defn- record-the-tallys [tallys]
  (doall (map #(spit @json-file-name (str (json/generate-string %) "\n") :append true) tallys)))

(defn- read-the-tallys []
  (if (file/file-exists @json-file-name)
    (with-open [rdr (reader @json-file-name)]
      (vec (map #(vec (json/parse-string % true)) (doall (line-seq rdr)))))))

(defrecord JSON [] repository/DataType
  (repository/alternate-file-name [this name] (use-different-file-name name))
  (repository/clear-all-data [this] (file/clear-file @json-file-name))
  (repository/read-tally [this] (read-the-tallys))
  (repository/record-player-tallys [this tally] (record-the-tallys tally)))
