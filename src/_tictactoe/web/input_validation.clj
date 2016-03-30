(ns -tictactoe.web.input_validation)

(def valid-dimensions ["3" "4"])

(def first-player-options ["player-one-marker" "player-two-marker"])

(defn- marker-is-valid [marker]
  (re-matches #"[A-Z]" marker))

(defn- name-exists [name]
  (not (clojure.string/blank? name)))

(defn- dimension-valid [dimension]
  (some #(= % dimension) valid-dimensions))

(defn- first-player-valid [first-player]
  (some #(= % first-player) first-player-options))

(defn input-sanitation [params]
  (and (every? #(name-exists %) [(:player-one-name params) (:player-two-name params)])
       (every? #(marker-is-valid %) [(:player-one-marker params) (:player-two-marker params)])
       (not= (:player-one-marker params) (:player-two-marker params))
       (not= (:player-one-name params) (:player-two-name params))
       (dimension-valid (:board-dimension params))
       (first-player-valid (:first-player params))))

(defn player-is-ai [player-type board-dimension]
  (and (= player-type "AI")
       (= board-dimension "3")))

(defn- player-spot-input-valid [board spot current-player-is-ai]
  (and (number? spot)
       (number? (get board spot))
       (not current-player-is-ai)))

(defn- ai-spot-input-valid [spot current-player-is-ai]
  (and current-player-is-ai
       (nil? spot)))

(defn spot-input-is-valid [board spot current-player-is-ai]
  (or (player-spot-input-valid board spot current-player-is-ai)
      (ai-spot-input-valid spot current-player-is-ai)))
