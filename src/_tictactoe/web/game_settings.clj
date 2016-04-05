(ns -tictactoe.web.game_settings)

(def valid-dimensions ["3" "4"])

(def valid-first-players ["player-one-marker" "player-two-marker"])

(def valid-player-types ["AI" "Human"])

(defn- get-A-Z-char []
  (map char (range (int \A) (inc (int \Z)))))

(defn- is-selected [selected value]
  (= selected value))

(defn markers-map [selected-marker]
  (let [A-Z (get-A-Z-char)]
    (map #(zipmap [:character :selected] [% (is-selected (.charAt selected-marker 0) %)]) A-Z)))

(defn board-dimension-map [selected-dimension]
  (map #(zipmap [:dimension :selected] [% (is-selected selected-dimension %)]) valid-dimensions))

(defn first-player-map [selected-player player-one player-two]
  (map #(zipmap [:player :player-title :selected] [%1 %2 (is-selected selected-player %1)])
                valid-first-players [player-one player-two]))

(defn player-type-selected [selected-type]
  (map #(is-selected selected-type %) valid-player-types))

(defn player-type-map [selected-type ai human]
  (map #(zipmap [:player-type :player-type-display :selected] [%1 %2 (is-selected selected-type %1)]) valid-player-types [ai human]))

(defn default-settings [name]
  {:player-one-name (str name "1")
   :player-two-name (str name "2")
   :player-one-marker "X"
   :player-two-marker "O"
   :player-one-type (second valid-player-types)
   :player-two-type (first valid-player-types)
   :board-dimension (first valid-dimensions)
   :first-player (first valid-first-players)})
