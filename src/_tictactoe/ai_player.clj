(ns -tictactoe.ai_player
  (:require [-tictactoe.game_functions :as gf]))

(defn get-available-spots [board]
  (vec (filter number? board)))

(defn winning-move-possible [board player-marker]
  (some #(gf/game-is-won %) (map #(gf/mark-board-location board % player-marker) (get-available-spots board))))

(defn score [board current-player player-marker depth]
  (cond (and (gf/game-is-won board)
             (= current-player player-marker)) (- depth 100)
        (and (gf/game-is-won board)
             (not (= current-player player-marker))) (- 100 depth)
        (winning-move-possible board current-player) 50
        (gf/game-is-tied board) 0))

(defn move
  [board player-marker current-player other-player-marker depth previous-move multiplier best-moves]
    (if (gf/game-is-won-or-tied board)
        (let [score (* multiplier (score board current-player player-marker depth))]
          (if (< (get @best-moves previous-move) score)
            (swap! best-moves assoc previous-move score)))
      (doseq [spot (get-available-spots board)]
        (move (gf/mark-board-location board spot current-player)
        player-marker
        other-player-marker
        current-player
        (inc depth)
        spot
        (* multiplier -1)
        best-moves))))

(defn get-best-move [best-moves]
  (first (first (filter (fn [[k v]] (= v (val (apply max-key val best-moves)))) best-moves))))

(defn best-move [board player-marker other-player-marker]
  (let [best-moves (atom (zipmap (get-available-spots board) (replicate (count (get-available-spots board)) -1000)))
    depth 0
    multiplier 1]
      (move board player-marker other-player-marker player-marker 0 nil multiplier best-moves)
    (get-best-move @best-moves)))
