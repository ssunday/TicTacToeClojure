;Responsible for ending the REPL application.

(ns -tictactoe.end_application_option
  (:require [-tictactoe.game_menu :as menu]))

(defmethod menu/do-menu-option :end-application [args]
  (System/exit 0))
