;Responsible for reading the total tallys.

(ns -tictactoe.score_reading
  (:require [-tictactoe.scoring_schema :as schema]
            [-tictactoe.scoring_repository :as repository]
            [-tictactoe.score_unique_player_names :as names]))

(defn read-total-tally [data]
  (let [tally (repository/read-tally data)
        total-tally (atom (zipmap (names/player-names data) (repeat schema/default-wins-losses-draws-scores)))]
    (doseq [tally-set tally]
      (doall (map #(swap! total-tally update-in [(first tally-set) %] + ((second tally-set) %)) schema/wins-losses-draws)))
    (vec @total-tally)))
