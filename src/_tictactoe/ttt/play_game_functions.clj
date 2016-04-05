(ns -tictactoe.ttt.play_game_functions
  (:require [-tictactoe.ttt.game_functions :as gf]
            [-tictactoe.ttt.ai_player :as ai]
            [-tictactoe.ttt.scoring_schema :as schema]))

(defn make-board [dimension]
  (gf/make-default-board dimension))

(defn get-other-player-marker [current-player player-one-marker player-two-marker]
  (if (= current-player player-one-marker)
      player-two-marker
      player-one-marker))

(defn current-player-is-ai [args]
  (or (and (= (:player-one-marker args) (:current-player args))
           (:player-one-is-ai args))
      (and (= (:player-two-marker args) (:current-player args))
           (:player-two-is-ai args))))
           
(defn ai-move [board player-marker other-player-marker]
  (ai/best-move board player-marker other-player-marker))

(defn mark-board [board spot current-player]
  (gf/mark-board-location board spot current-player))

(defn game-is-over [board]
  (gf/game-is-over board))

(defn game-tied [board]
  (gf/game-is-tied board))

(defn player-won [board player-marker]
  (= (gf/game-is-won board) player-marker))

(defn score-game-round [args]
  (let [player-one-name (:player-one-name args)
        player-two-name (:player-two-name args)
        board (:board args)
        player-one-marker (:player-one-marker args)
        player-two-marker (:player-two-marker args)
        winning-score {schema/wins 1 schema/losses 0 schema/draws 0}
        losing-score {schema/wins 0 schema/losses 1 schema/draws 0}
        draw-score {schema/wins 0 schema/losses 0 schema/draws 1}]
    (cond (player-won board player-one-marker) [[player-one-name winning-score]
                                                [player-two-name losing-score]]
          (player-won board player-two-marker) [[player-one-name losing-score]
                                                [player-two-name winning-score]]
          (game-tied board) [[player-one-name draw-score]
                             [player-two-name draw-score]])))
