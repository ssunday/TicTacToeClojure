;Responsible for running the menu loop.

(ns -tictactoe.game_runner
  (:require [-tictactoe.game_menu :as menu]
            [-tictactoe.game_menu_messages :as messages]))

(defn run []
  (messages/start-game-message)
  (loop [menu-option (messages/select-menu-option menu/menu-options)]
    (menu/do-menu-option menu-option)
    (recur (messages/select-menu-option menu/menu-options))))
