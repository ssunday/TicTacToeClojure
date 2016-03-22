;Responsible for getting the unique player names recorded in the particular file

(ns -tictactoe.score_unique_player_names
  (:require [-tictactoe.scoring_repository :as repository]))

(defn player-names [data]
  (let [player-tally (repository/read-tally data)]
    (distinct (map #(first %) player-tally))))
