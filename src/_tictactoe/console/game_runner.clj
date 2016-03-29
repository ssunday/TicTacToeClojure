;Responsible for running the menu loop.

(ns -tictactoe.console.game_runner
  (:require [-tictactoe.console.game_menu :as menu]
            [-tictactoe.console.game_menu_messages :as messages]
            [-tictactoe.console.play_game_option :refer :all]
            [-tictactoe.console.display_previous_scores_option :refer :all]
            [-tictactoe.console.end_application_option :refer :all]
            [-tictactoe.console.game_runner :as runner]))

(defn run [data]
  (messages/start-game-message)
  (loop [menu-option (messages/select-menu-option menu/menu-options)]
    (menu/do-menu-option {:option menu-option :data data})
    (recur (messages/select-menu-option menu/menu-options))))
