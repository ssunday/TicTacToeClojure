;Responsible for the core scoring and recording functions.

(ns -tictactoe.scoring_repository)

(defmulti alternate-file-name
  (fn [file]
    (:file-type file)))

(defmulti read-tally identity)

(defmulti clear-all-data identity)

(defmulti record-player-tallys (fn [data] (:data-type data)))
