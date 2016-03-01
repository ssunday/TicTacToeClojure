(ns -tictactoe.ai_player
  (:require [-tictactoe.game_functions :as gf]))

(defn get-available-locations [board]
  (vec (filter number? board)))

(defn score [board current-player player-marker depth]
  (cond (and (gf/game-is-won board)
              (= current-player player-marker)) (- 10 depth)
        (and (gf/game-is-won board)
                    (not (= current-player player-marker))) (- depth 10)
        (gf/game-is-tied board) 0)
)

(defn best-move
  [board player-marker other-player-marker]
  (let [best-moves (atom (zipmap (get-available-locations board) [0 0 0 0 0 0 0 0 0]))]
  (loop [board board
         current-player player-marker
         other-player other-player-marker
         depth 0
         previous-move nil]
        (if (gf/game-is-won-or-tied board)
            (swap! best-moves assoc previous-move (score board current-player player-marker depth))
            (recur (gf/mark-board-location board (first (get-available-locations board)) current-player)
            other-player
            current-player
            (inc depth)
            (first (get-available-locations board)))))
  (key (apply max-key val @best-moves)))
)
