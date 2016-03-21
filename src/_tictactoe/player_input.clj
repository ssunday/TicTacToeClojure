;Responsible for asking for player input

(ns -tictactoe.player_input
  (:require [-tictactoe.input_validation :as validation]
            [-tictactoe.message_writer :as writer]
            [-tictactoe.locale :as locale])
  (:use [-tictactoe.localization :only (translate)]))

(defn get-player-spot-to-be-marked [board]
  (writer/write (translate (locale/loc) :input/spot-to-be-marked (dec (count board))))
  (loop [spot (validation/convert-string-to-number (read-line))]
    (if (validation/spot-is-invalid board spot)
      (do (cond (validation/spot-is-invalid-input spot) (writer/write (translate (locale/loc) :error-messages/spot-is-invalid-input))
                (validation/spot-is-not-on-board board spot) (writer/write (translate (locale/loc) :error-messages/spot-is-not-on-board))
                (validation/spot-is-already-marked board spot) (writer/write (translate (locale/loc) :error-messages/spot-not-open)))
          (recur (validation/convert-string-to-number (read-line))))
      spot)))
