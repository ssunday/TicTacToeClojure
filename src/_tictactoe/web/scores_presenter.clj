(ns -tictactoe.web.scores_presenter
  (:require [-tictactoe.ttt.scoring_schema :as schema]))

(defn- format-score [player-name scores]
  (str "<tr><td>" player-name "</td><td>"
      (scores schema/wins) "/" (scores schema/losses) "/" (scores schema/draws) "</td></tr>"))

(defn- format-tallys [tally]
  (map #(format-score (first %) (second %)) tally))

(defn- create-table [tallys name-header tally-header]
  (str "<table border='1'cellpadding='10'><tr><th>" name-header "</th><th>" tally-header "</th>" tallys "</tr></table>"))

(defn display-scores [tally name-header tally-header]
  (create-table (clojure.string/join (format-tallys tally)) name-header tally-header))
