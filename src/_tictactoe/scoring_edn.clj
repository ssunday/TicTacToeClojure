(ns -tictactoe.scoring_edn
  (:require [clojure.edn :as edn]
            [-tictactoe.scoring_repository :as repository]
            [-tictactoe.file_type_functions :as file])
  (:use [clojure.java.io]))

(def edn-file-name "tally.edn")

(defmethod repository/alternate-file-name :edn [file]
  (def edn-file-name (str (:file-name file) ".edn")))

(defmethod repository/clear-all-data :edn [file]
  (file/clear-file edn-file-name))

(defmethod repository/record-player-tallys :edn [player-tally]
  (doall (map #(spit edn-file-name (prn-str %) :append true) (:tally player-tally))))

(defmethod repository/read-tally :edn [data]
  (if (file/file-exists edn-file-name)
    (with-open [rdr (reader edn-file-name)]
        (map #(edn/read-string %) (doall (line-seq rdr))))))
