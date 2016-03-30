;Creates the particular record for the chosen data type

(ns -tictactoe.data_storage_factory
  (:require [-tictactoe.scoring.scoring_edn :refer :all]
            [-tictactoe.scoring.scoring_json :refer :all]
            [-tictactoe.scoring.scoring_postgres :refer :all]
            [-tictactoe.ttt.data_storage_type :as data-storage]))

(defn data []
  (cond (= (data-storage/data-type) :json) (->JSON)
        (= (data-storage/data-type) :edn) (->EDN)
        (= (data-storage/data-type) :pg) (->POSTGRES)))
