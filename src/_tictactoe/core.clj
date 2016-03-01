(ns -tictactoe.core
  (:require [-tictactoe.game_functions :as gf]
            [-tictactoe.console_io :as io]
            [-tictactoe.ai_player :as ai]))

(defn display-end-game-messages [board player-marker player-one-marker]
  (if (gf/game-is-won board)
      (if (= player-marker player-one-marker)
          (io/player-two-won-message)
          (io/player-one-won-message))
      (io/game-is-tied-message)))

(defn get-move [board current-player-marker other-player-marker current-player-is-ai]
  (if current-player-is-ai
    (gf/mark-board-location board (ai/best-move board current-player-marker other-player-marker) current-player-marker)
    (gf/mark-board-location board (io/get-player-spot-to-be-marked board) current-player-marker)
  )
)

(defn -main []
  (io/start-game-message)
  (loop [play-again true]
    (if play-again
      (do
        (let [player-one-marker (io/get-player-one-marker)
              player-two-marker (io/get-player-two-marker player-one-marker)
              first-player (io/get-first-player player-one-marker player-two-marker)
              player-one-is-ai (io/get-whether-player-one-is-ai)
              player-two-is-ai (io/get-whether-player-two-is-ai)
              board (gf/make-default-board)]
          (loop [player-marker first-player
                other-player-marker (if (= first-player player-one-marker) player-two-marker player-one-marker)
                board board]
              (if (not (gf/game-is-won-or-tied board))
                  (do (io/display-current-player-marker player-marker)
                      (io/display-game-board board)
                      (recur other-player-marker player-marker
                            (get-move board player-marker other-player-marker
                                (if (= player-marker player-one-marker) player-one-is-ai player-two-is-ai))))
                  (display-end-game-messages board player-marker player-one-marker))))
        (recur (io/ask-if-player-wants-to-play-again)))
        (io/end-game-message)))
)
