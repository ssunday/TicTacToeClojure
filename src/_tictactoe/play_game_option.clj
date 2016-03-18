;Responsible for the menu option of running the game.

(ns -tictactoe.play_game_option
  (:require [-tictactoe.game_menu :as menu])
  (:use [-tictactoe.play_game :only (run-game)]))

(defmethod menu/do-menu-option :play-game [data]
  (run-game))
