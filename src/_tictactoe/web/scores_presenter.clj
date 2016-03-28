(ns -tictactoe.web.scores_presenter
  (:require [-tictactoe.ttt.scoring_schema :as schema]))

(defn- format-score [player-name scores tally-header]
  (str player-name ":<br>" tally-header "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
      (scores schema/wins) "/" (scores schema/losses) "/" (scores schema/draws) "<br><br>"))

(defn- format-tallys [tally tally-header]
  (map #(format-score (first %) (second %) tally-header) tally))

(defn display-scores [tally tally-header]
  (clojure.string/join (format-tallys tally tally-header)))
