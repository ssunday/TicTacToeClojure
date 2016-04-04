(ns -tictactoe.web.input_validation
  (:require [-tictactoe.ttt.validation :as validation])
  (:use [-tictactoe.ttt.convert_string_to_number :only (convert-string-to-number)]))

(defn input-sanitation [params]
  (and (every? #(validation/name-exists %) [(:player-one-name params) (:player-two-name params)])
       (every? #(validation/marker-is-valid %) [(:player-one-marker params) (:player-two-marker params)])
       (not= (:player-one-marker params) (:player-two-marker params))
       (not= (:player-one-name params) (:player-two-name params))
       (not (validation/invalid-board-dimension (convert-string-to-number (:board-dimension params))))
       (not (validation/first-player-invalid (:first-player params) "player-one-marker" "player-two-marker"))))

(defn player-is-ai [player-type board-dimension]
  (and (= player-type "AI")
       (= board-dimension "3")))

(defn- player-spot-input-valid [board spot current-player-is-ai]
  (and (validation/spot-is-valid-location board spot)
       (not current-player-is-ai)))

(defn- ai-spot-input-valid [spot current-player-is-ai]
  (and current-player-is-ai
       (nil? spot)))

(defn spot-input-is-valid [board spot current-player-is-ai]
  (or (player-spot-input-valid board spot current-player-is-ai)
      (ai-spot-input-valid spot current-player-is-ai)))
