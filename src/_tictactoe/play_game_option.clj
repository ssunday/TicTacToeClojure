;Responsible for the menu option of running the game.

(ns -tictactoe.play_game_option
  (:require [-tictactoe.game_menu :as menu]
            [-tictactoe.play_game :as play_game]))

(defmethod menu/do-menu-option :play-game [args]
  (play_game/run-game (:data args)))
