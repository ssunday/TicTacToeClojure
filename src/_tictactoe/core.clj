(ns -tictactoe.core
  (:require [-tictactoe.game_functions :as gf])
  (:require [-tictactoe.console_io :as io]))

(defn display-end-game-messages [board player-marker player-one-marker]
  (if (gf/game-is-won board)
      (if (= player-marker player-one-marker)
          (io/player-two-won-message)
          (io/player-one-won-message))
      (io/game-is-tied-message)))
      
(defn -main []
  (io/start-game-message)
  (let [player-one-marker (io/get-player-one-marker)
        player-two-marker (io/get-player-two-marker player-one-marker)
        board (gf/make-default-board)]
    (loop [player-marker player-one-marker
          other-player-marker player-two-marker
          board board]
        (if (not (gf/game-is-won-or-tied board))
            (do (io/display-game-board board)
                (recur other-player-marker player-marker
                      (gf/mark-board-location board (io/get-player-spot-to-be-marked board) player-marker)))
            (display-end-game-messages board player-marker player-one-marker))))
  (io/end-game-message)
)
