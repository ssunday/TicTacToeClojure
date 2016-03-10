(ns -tictactoe.ai_player
  (:require [-tictactoe.game_functions :as gf]))

(defn get-available-spots [board]
  (vec (filter number? board)))

(defn get-score [board current-player player-marker depth]
  (cond (and (gf/game-is-won board)
             (= current-player player-marker)) (- depth 10)
        (and (gf/game-is-won board)
             (not (= current-player player-marker))) (- 10 depth)
        (gf/game-is-tied board) 0))

(defn update-best-move-score [best-moves board current-player player-marker previous-move multiplier depth]
  (let [score (* multiplier (get-score board current-player player-marker (dec depth)))]
    (if (< (get @best-moves previous-move) score)
      (swap! best-moves assoc previous-move score))))

(defn move
  [board player-marker current-player other-player-marker depth previous-move multiplier best-moves]
    (if (gf/game-is-won-or-tied board)
        (update-best-move-score best-moves board current-player player-marker previous-move multiplier depth)
        (dorun (map #(move (gf/mark-board-location board % current-player)
                            player-marker other-player-marker current-player
                            (inc depth) % (* multiplier -1) best-moves) (get-available-spots board)))))

(defn max-score [best-moves]
  (val (apply max-key val best-moves)))

(defn all-instances-of-max-score [best-moves]
  (filter #(= (val %) (max-score best-moves)) best-moves))

(defn get-best-of-the-best [all-best-moves]
  (key (first all-best-moves)))

(defn get-best-move [best-moves]
  (get-best-of-the-best (all-instances-of-max-score best-moves)))

(defn best-move [board player-marker other-player-marker]
  (let [best-moves (atom (zipmap (get-available-spots board) (replicate (count (get-available-spots board)) -1000)))]
      (dorun (map #(move (gf/mark-board-location board % player-marker)
                          player-marker other-player-marker player-marker
                          0 % 1 best-moves) (get-available-spots board)))
      (get-best-move @best-moves)))
