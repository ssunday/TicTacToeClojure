(ns -tictactoe.game_runner
  (:require [-tictactoe.console_io :as io]
            [-tictactoe.game_menu :as menu]))

(defn run []
  (io/start-game-message)
  (loop [menu-option (io/select-menu-option menu/menu-options)]
    (menu/do-menu-option menu-option)
    (recur (io/select-menu-option menu/menu-options))))
