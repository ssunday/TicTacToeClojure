;Responsible for the core scoring and recording functions.

(ns -tictactoe.ttt.scoring_repository)

(defprotocol DataType
  (alternate-file-name [this name])
  (clear-all-data [this])
  (read-tally [this])
  (record-player-tallys [this tally]))
