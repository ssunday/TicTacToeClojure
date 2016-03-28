;Responsible for displaying tallys

(ns -tictactoe.console.display_tally
  (:require [-tictactoe.ttt.scoring_schema :as scoring_schema]
            [-tictactoe.console.message_writer :as writer]
            [-tictactoe.ttt.locale :as locale])
  (:use [-tictactoe.ttt.localization :only (translate)]))

(defn display-tally [tally]
  (writer/write (translate (locale/loc) :output/player-tally))
  (doseq [[player-name scores] tally]
      (writer/write (str player-name ":\n"
                  (translate (locale/loc) :output/tally-header) ":\t"
                  (scores scoring_schema/wins) "/" (scores scoring_schema/losses) "/" (scores scoring_schema/draws)))))
