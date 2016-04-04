(ns -tictactoe.web.game_settings)

(defn- get-A-Z-char []
  (map char (range (int \A) (inc (int \Z)))))

(defn- is-selected [selected value]
  (= selected value))

(defn markers-map [selected-marker]
  (let [A-Z (get-A-Z-char)]
    (map #(zipmap [:character :selected] [% (is-selected (.charAt selected-marker 0) %)]) A-Z)))

(defn board-dimension-map [selected-dimension]
  (map #(zipmap [:dimension :selected] [% (is-selected selected-dimension %)]) ["3" "4"]))

(defn first-player-map [selected-player player-one player-two]
  (map #(zipmap [:player :player-title :selected] [%1 %2 (is-selected selected-player %1)])
                ["player-one-marker" "player-two-marker"] [player-one player-two]))

(defn player-type-map [selected-type]
  (map #(zipmap [:player-type :selected] [%1 (is-selected selected-type %1)]) ["AI" "Human"]))

(defn default-settings [name]
  {:player-one-name (str name "1")
   :player-two-name (str name "2")
   :player-one-marker "X"
   :player-two-marker "O"
   :player-one-type "Human"
   :player-two-type "AI"
   :board-dimension "3"
   :first-player "player-one-marker"})
