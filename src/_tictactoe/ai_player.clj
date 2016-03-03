(ns -tictactoe.ai_player
  (:require [-tictactoe.game_functions :as gf]))

(defn get-available-locations [board]
  (vec (filter number? board)))

(defn score [board current-player player-marker depth]
  (cond (gf/game-is-won board) (- 10 depth)
        (gf/game-is-tied board) 0))


(defn move
  [board player-marker current-player other-player-marker depth previous-move multiplier best-moves]
    (if (gf/game-is-won-or-tied board)
        (if (< (get @best-moves previous-move) (* multiplier (score board current-player player-marker depth)))
          (swap! best-moves assoc previous-move (* multiplier (score board current-player player-marker depth))))
      (move (gf/mark-board-location board (first (get-available-locations board)) current-player)
        player-marker
        other-player-marker
        current-player
        (inc depth)
        (first (get-available-locations board))
        (* multiplier -1)
        best-moves)))

(defn get-best-move [best-moves]
  (first (first (filter (fn [[k v]] (= v (val (apply max-key val best-moves)))) best-moves))))

(defn best-move [board player-marker other-player-marker]
  (let [best-moves (atom (zipmap (get-available-locations board) (replicate (count (get-available-locations board)) -500)))
    depth 0
    multiplier 1]
      (doseq [spot (get-available-locations board)]
      (move (gf/mark-board-location board spot other-player-marker)
              player-marker player-marker other-player-marker depth spot multiplier best-moves)
      (move (gf/mark-board-location board spot player-marker)
              player-marker other-player-marker player-marker depth spot multiplier best-moves))
    (get-best-move @best-moves)))
