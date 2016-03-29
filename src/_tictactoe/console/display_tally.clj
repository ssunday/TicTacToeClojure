;Responsible for displaying tallys

(ns -tictactoe.console.display_tally
  (:require [-tictactoe.ttt.scoring_schema :as scoring_schema]
            [-tictactoe.console.message_writer :as writer])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.locale :only (loc)]))

(defn display-tally [tally]
  (writer/write (translate (loc) :output/player-tally))
  (doseq [[player-name scores] tally]
      (writer/write (str player-name ":\n"
                    (translate (loc) :output/tally-header) ":\t"
                    (scores scoring_schema/wins) "/" (scores scoring_schema/losses) "/" (scores scoring_schema/draws)))))
