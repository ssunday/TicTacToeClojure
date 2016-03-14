(ns -tictactoe.ai_player
  (:require [-tictactoe.game_functions :as gf]))

(defn get-available-locations [board]
  (vec (filter number? board)))

(defn player-markers [ai-marker opponent-marker]
  {:ai ai-marker :opponent opponent-marker})

(defn get-score [board player-markers depth]
  (let [winning-player (gf/game-is-won board)]
  (cond (= winning-player (:ai player-markers)) (- 100 depth)
        (= winning-player (:opponent player-markers)) (- depth 100)
        (gf/game-is-tied board) 0)))

(defn minimax [board player-markers current-player-marker depth]
  (if (gf/game-is-won-or-tied board)
    (get-score board player-markers depth)
    (if (= current-player-marker (:ai player-markers))
        (apply max (map #(minimax (gf/mark-board-location board % current-player-marker)
                                  player-markers (:opponent player-markers) (inc depth))
                  (get-available-locations board)))
        (apply min (map #(minimax (gf/mark-board-location board % current-player-marker)
                                  player-markers (:ai player-markers) (inc depth))
                  (get-available-locations board))))))

(defn scores-for-available-locations [board player-markers]
  (let [initial-depth 1]
      (pmap #(minimax (gf/mark-board-location board % (:ai player-markers)) player-markers (:opponent player-markers) initial-depth)
                      (get-available-locations board))))

(defn assign-scores-to-available-location [board player-markers]
  (zipmap (get-available-locations board) (scores-for-available-locations board player-markers)))

(defn best-move [board ai-marker opponent-marker]
  (key (apply max-key val (assign-scores-to-available-location board (player-markers ai-marker opponent-marker)))))
