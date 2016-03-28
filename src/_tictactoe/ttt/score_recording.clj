;Responsible for recording player tallys.

(ns -tictactoe.ttt.score_recording
  (:require [-tictactoe.ttt.scoring_repository :as repository]))

(defn record-tally [data player-tally]
  (repository/record-player-tallys data player-tally))
