;Responsible for the core scoring and recording functions.

(ns -tictactoe.scoring_repository)

(defmulti alternate-file-name
  (fn [file]
    (:file-type file)))

(defmulti clear-all-data identity)

(defmulti read-tally identity)

(defmulti record-player-tallys (fn [data] (:data-type data)))
