;Responsible for generating the AI player's best move by way of minimax.

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

(defn apply-max-or-min [minimax-map player-markers current-player-marker]
  (if (= current-player-marker (:ai player-markers))
      (apply max minimax-map)
      (apply min minimax-map)))

(defn get-next-player [player-markers current-player-marker]
  (if (= current-player-marker (:ai player-markers))
      (:opponent player-markers)
      (:ai player-markers)))

(defn minimax [board player-markers current-player-marker depth]
  (if (gf/game-is-over board)
    (get-score board player-markers depth)
    (let [next-player (get-next-player player-markers current-player-marker)]
      (apply-max-or-min (map #(minimax (gf/mark-board-location board % current-player-marker)
                                              player-markers next-player (inc depth))
                              (get-available-locations board))
        player-markers current-player-marker))))

(defn scores-for-available-locations [board player-markers]
  (pmap #(minimax (gf/mark-board-location board % (:ai player-markers)) player-markers (:opponent player-markers) 1)
         (get-available-locations board)))

(defn assign-scores-to-available-location [board player-markers]
  (zipmap (get-available-locations board)
          (scores-for-available-locations board player-markers)))

(defn get-best-move [moves-and-scores]
  (key (apply max-key val moves-and-scores)))

(defn best-move [board ai-marker opponent-marker]
  (get-best-move (assign-scores-to-available-location board (player-markers ai-marker opponent-marker))))
