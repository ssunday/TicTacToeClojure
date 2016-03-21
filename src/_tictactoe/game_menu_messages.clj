(ns -tictactoe.game_menu_messages
  (:require [-tictactoe.input_validation :as validation]
            [-tictactoe.message_writer :as writer]
            [-tictactoe.locale :as locale])
  (:use [-tictactoe.localization :only (translate)]))

(defn start-game-message []
  (writer/write (translate (locale/loc) :output/welcome-message)))

(defn select-menu-option [menu-options]
  (writer/write (translate (locale/loc) :menu/menu))
  (doseq [[menu-number, menu-option] menu-options]
        (writer/write (str menu-number ". " (translate (locale/loc) (keyword "menu" menu-option)))))
  (writer/write (translate (locale/loc) :menu/select-menu-option))
  (loop [option (validation/convert-string-to-number (read-line))]
        (if (validation/menu-option-is-valid (count menu-options) option)
            option
            (do (writer/write (translate (locale/loc) :error-messages/invalid-menu-option))
                (recur (validation/convert-string-to-number (read-line)))))))
