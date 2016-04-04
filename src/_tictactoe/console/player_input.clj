;Responsible for asking for player input

(ns -tictactoe.console.player_input
  (:require [-tictactoe.ttt.validation :as validation]
            [-tictactoe.console.message_writer :as writer]
            [-tictactoe.ttt.convert_string_to_number :as convert])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.locale :only (loc)]))

(defn- spot-is-not-on-board [board spot]
  (nil? (find board spot)))

(defn- spot-is-invalid-input [spot]
  (nil? spot))

(defn- spot-is-already-marked [board spot]
  (string? (get board spot)))

(defn get-player-spot-to-be-marked [board]
  (writer/write (translate (loc) :input/spot-to-be-marked (dec (count board))))
  (loop [spot (convert/convert-string-to-number (read-line))]
    (if (not (validation/spot-is-valid-location board spot))
      (do (cond (spot-is-invalid-input spot) (writer/write (translate (loc) :error-messages/spot-is-invalid-input))
                (spot-is-not-on-board board spot) (writer/write (translate (loc) :error-messages/spot-is-not-on-board))
                (spot-is-already-marked board spot) (writer/write (translate (loc) :error-messages/spot-not-open)))
          (recur (convert/convert-string-to-number (read-line))))
      spot)))
