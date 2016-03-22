;Responsible for recording player tallys.

(ns -tictactoe.score_recording
  (:require [-tictactoe.scoring_repository :as repository]))

(defn record-tally [data player-tally]
  (repository/record-player-tallys data player-tally))
