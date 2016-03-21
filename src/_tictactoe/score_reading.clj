;Responsible for reading the total tallys.

(ns -tictactoe.score_reading
  (:require [-tictactoe.scoring_schema :as schema]
            [-tictactoe.scoring_repository :as repository]
            [-tictactoe.score_unique_player_names :as names]
            [-tictactoe.data_storage_type :as data]))

(defn read-total-tally []
  (let [tally (repository/read-tally (data/data-type))
        total-tally (atom (zipmap (names/player-names) (repeat schema/default-wins-losses-draws-scores)))]
    (doseq [tally-set tally]
      (doall (map #(swap! total-tally update-in [(first tally-set) %] + ((second tally-set) %)) schema/wins-losses-draws)))
    (vec @total-tally)))
