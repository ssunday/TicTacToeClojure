;Responsible for recording player tallys.

(ns -tictactoe.score_recording
  (:require [-tictactoe.scoring_repository :as repository]
            [-tictactoe.data_storage_type :as data]))

(defn record-tally [player-tally]
  (repository/record-player-tallys {:data-type (data/data-type) :tally player-tally}))
