;Responsible for asking for player input

(ns -tictactoe.console.player_input
  (:require [-tictactoe.console.input_validation :as validation]
            [-tictactoe.console.message_writer :as writer]
            [-tictactoe.ttt.convert_string_to_number :as convert])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.locale :only (loc)]))

(defn get-player-spot-to-be-marked [board]
  (writer/write (translate (loc) :input/spot-to-be-marked (dec (count board))))
  (loop [spot (convert/convert-string-to-number (read-line))]
    (if (validation/spot-is-invalid board spot)
      (do (cond (validation/spot-is-invalid-input spot) (writer/write (translate (loc) :error-messages/spot-is-invalid-input))
                (validation/spot-is-not-on-board board spot) (writer/write (translate (loc) :error-messages/spot-is-not-on-board))
                (validation/spot-is-already-marked board spot) (writer/write (translate (loc) :error-messages/spot-not-open)))
          (recur (convert/convert-string-to-number (read-line))))
      spot)))
