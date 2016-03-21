;Responsible for miscellaneous functions used for testing.

(ns -tictactoe.score_data_functions_for_testing
  (:require [-tictactoe.scoring_repository :as repository]
            [-tictactoe.data_storage_type :as data]))

(defn different-file-name [file-name]
  (repository/alternate-file-name {:file-type (data/data-type) :file-name file-name}))

(defn clear-data []
  (repository/clear-all-data (data/data-type)))
