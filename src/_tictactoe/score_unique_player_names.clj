(ns -tictactoe.score_unique_player_names
  (:require [-tictactoe.scoring_repository :as repository]
            [-tictactoe.data_storage_type :as data]))

(defn player-names []
  (let [player-tally (repository/read-tally (data/data-type))]
    (distinct (map #(first %) player-tally))))
