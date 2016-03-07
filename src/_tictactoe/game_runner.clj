(ns -tictactoe.game_runner
  (:require [-tictactoe.console_io :as io]
            [-tictactoe.game_menu :as menu]))

(defn run []
  (io/start-game-message)
  (loop [menu-option (io/select-menu-option (menu/get-menu-options))]
    (if (not (menu/do_menu_option menu-option))
      (recur (io/select-menu-option (menu/get-menu-options))))))
