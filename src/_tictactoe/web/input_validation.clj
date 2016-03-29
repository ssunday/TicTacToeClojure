(ns -tictactoe.web.input_validation)

(defn marker-and-name-validation [player-one-marker player-two-marker player-one-name player-two-name]
  (and (not= player-one-marker player-two-marker)
       (not (clojure.string/blank? player-one-name))
       (not (clojure.string/blank? player-two-name))
       (not= player-one-name player-two-name)))

(defn player-is-ai [player-type board-dimension]
  (and (= player-type "AI")
       (= board-dimension "3")))
