(ns -tictactoe.web.scores_presenter
  (:require [-tictactoe.ttt.scoring_schema :as schema]))

(defn- format-score [player-name scores]
  (str player-name ":<br>W/L/D&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
      (scores schema/wins) "/" (scores schema/losses) "/" (scores schema/draws) "<br><br>"))

(defn display-scores [tally]
  (let [string-scores (atom ())]
    (doseq [[player-name scores] tally]
        (swap! string-scores conj (format-score player-name scores)))
    (clojure.string/join (reverse @string-scores))))
