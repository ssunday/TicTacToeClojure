(ns -tictactoe.game_runner
  (:use [-tictactoe.console_io :only (start-game-message select-menu-option)]
        [-tictactoe.game_menu :only (do-menu-option menu-options)]))

(defn run []
  (start-game-message)
  (loop [menu-option (select-menu-option menu-options)]
    (do-menu-option menu-option)
    (recur (select-menu-option menu-options))))
