;Responsible for getting the unique player names recorded in the particular file

(ns -tictactoe.ttt.score_unique_player_names
  (:require [-tictactoe.ttt.scoring_repository :as repository]))

(defn player-names [data]
  (let [player-tally (repository/read-tally data)]
    (distinct (map #(first %) player-tally))))
