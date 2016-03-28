;Responsible for the menu option of running the game.

(ns -tictactoe.console.play_game_option
  (:require [-tictactoe.console.game_menu :as menu]
            [-tictactoe.console.play_game :as play_game]))

(defmethod menu/do-menu-option :play-game [args]
  (play_game/run-game (:data args)))
