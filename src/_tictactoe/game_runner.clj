;Responsible for running the menu loop.

(ns -tictactoe.game_runner
  (:require [-tictactoe.game_menu :as menu]
            [-tictactoe.game_menu_messages :as messages]))

(defn run [data]
  (messages/start-game-message)
  (loop [menu-option (messages/select-menu-option menu/menu-options)]
    (menu/do-menu-option {:option menu-option :data data})
    (recur (messages/select-menu-option menu/menu-options))))
