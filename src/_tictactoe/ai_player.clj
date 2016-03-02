(ns -tictactoe.ai_player
  (:require [-tictactoe.game_functions :as gf]))

(defn get-available-locations [board]
  (vec (filter number? board)))

(defn score [board current-player player-marker depth]
  (cond (and (gf/game-is-won board)
              (= current-player player-marker)) (- 10 depth)
        (and (gf/game-is-won board)
             (not (= current-player player-marker))) (- depth 10)
        (gf/game-is-tied board) 0))

(defn move
  [board player-marker current-player other-player-marker depth previous-move multiplier best-moves]
    (if (gf/game-is-won-or-tied board)
        (swap! best-moves assoc previous-move (* multiplier (score board current-player player-marker depth)))
      (move (gf/mark-board-location board (first (get-available-locations board)) current-player)
        player-marker
        other-player-marker
        current-player
        (inc depth)
        (first (get-available-locations board))
        (* multiplier -1)
        best-moves)))

(defn best-move [board player-marker other-player-marker]
  (let [best-moves (atom {})
    depth 0
    previous-move nil
    multiplier -1]
      (doseq [spot (get-available-locations board)]
        (move (gf/mark-board-location board spot player-marker)
        player-marker other-player-marker player-marker depth spot multiplier best-moves))
    (key (apply max-key val @best-moves))))
