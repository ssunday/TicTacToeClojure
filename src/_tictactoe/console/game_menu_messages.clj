(ns -tictactoe.console.game_menu_messages
  (:require [-tictactoe.console.message_writer :as writer]
            [-tictactoe.ttt.locale :as locale])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.locale :only (loc)]
        [-tictactoe.ttt.convert_string_to_number :only (convert-string-to-number)]))

(defn- menu-option-is-valid [menu-length option]
  (and (number? option)
       (<= option menu-length)
       (> option 0)))

(defn start-game-message []
  (writer/write (translate (loc) :output/welcome-message)))

(defn- display-menu [menu-options]
  (writer/write (translate (loc) :menu/menu))
  (doseq [[menu-number, menu-option] menu-options]
      (writer/write (str menu-number ". " (translate (loc) (keyword "menu" menu-option))))))

(defn select-menu-option [menu-options]
  (display-menu menu-options)
  (writer/write (translate (loc) :menu/select-menu-option))
  (loop [option (convert-string-to-number (read-line))]
    (if (menu-option-is-valid (count menu-options) option)
        option
        (do (writer/write (translate (loc) :error-messages/invalid-menu-option))
            (recur (convert-string-to-number (read-line)))))))
