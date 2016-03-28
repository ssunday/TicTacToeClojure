(ns -tictactoe.web.input_validation)

(defn markers-and-names-are-not-the-same [player-one-marker player-two-marker player-one-name player-two-name]
  (and (not= player-one-marker player-two-marker)
       (not= player-one-name player-two-name)))

(defn player-is-ai [player-type board-dimension]
  (and (= player-type "AI")
       (= board-dimension "3")))
