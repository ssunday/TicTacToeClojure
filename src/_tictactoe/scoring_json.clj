(ns -tictactoe.scoring_json
  (:require [cheshire.core :as json]
            [-tictactoe.scoring_repository :as repository]
            [-tictactoe.file_type_functions :as file])
  (:use [clojure.java.io]))

(def json-file-name "tally.json")

(defmethod repository/alternate-file-name :json [file]
  (def json-file-name (str (:file-name file) ".json")))

(defmethod repository/clear-all-data :json [file]
  (file/clear-file json-file-name))

(defmethod repository/record-player-tallys :json [player-tally]
  (doall (map #(spit json-file-name (str (json/generate-string %) "\n") :append true) (:tally player-tally))))

(defmethod repository/read-tally :json [data]
  (if (file/file-exists json-file-name)
    (with-open [rdr (reader json-file-name)]
      (vec (map #(vec (json/parse-string % true)) (doall (line-seq rdr)))))))
