;Responsible for displaying tallys

(ns -tictactoe.display_tally
  (:require [-tictactoe.scoring_schema :as scoring_schema]
            [-tictactoe.message_writer :as writer]
            [-tictactoe.locale :as locale])
  (:use [-tictactoe.localization :only (translate)]))

(defn display-tally [tally]
  (writer/write (translate (locale/loc) :output/player-tally))
  (doseq [[player-name scores] tally]
      (writer/write (str player-name ":\n"
                  (translate (locale/loc) :output/tally-header) ":\t"
                  (scores scoring_schema/wins) "/" (scores scoring_schema/losses) "/" (scores scoring_schema/draws)))))
