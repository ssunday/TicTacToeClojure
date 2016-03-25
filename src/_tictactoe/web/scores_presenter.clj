(ns -tictactoe.web.scores_presenter
  (:require [-tictactoe.ttt.scoring_schema :as schema]))

(defn- format-score [player-name scores tally-header]
  (str player-name ":<br>" tally-header "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
      (scores schema/wins) "/" (scores schema/losses) "/" (scores schema/draws) "<br><br>"))

(defn display-scores [tally tally-header]
  (let [string-scores (atom ())]
    (doseq [[player-name scores] tally]
        (swap! string-scores conj (format-score player-name scores tally-header)))
    (clojure.string/join (reverse @string-scores))))
