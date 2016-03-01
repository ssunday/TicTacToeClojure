(ns -tictactoe.ai_player
  (:require [-tictactoe.game_functions :as gf]))

(defn get-available-locations [board]
  (filter number? board))

(defn move
  [board player-marker other-player-marker]

)
