;Responsible for ending the REPL application.

(ns -tictactoe.console.end_application_option
  (:require [-tictactoe.console.game_menu :as menu]))

(defmethod menu/do-menu-option :end-application [_]
  (System/exit 0))
