(ns -tictactoe.web.scores_presenter
  (:require [-tictactoe.ttt.scoring_schema :as schema]))

(defn- parse-single-tally [single-tally]
  (str (single-tally schema/wins) "/" (single-tally schema/losses) "/" (single-tally schema/draws)))

(defn- parse-tallys [original-tally]
  (reduce (fn [adjusted-tally [name tally]]
              (assoc adjusted-tally name (parse-single-tally tally))) {} original-tally))

(defn get-parsed-scores [tally]
  (let [parsed-tallys (parse-tallys tally)]
    (map #(zipmap [:name :score] %) parsed-tallys)))
