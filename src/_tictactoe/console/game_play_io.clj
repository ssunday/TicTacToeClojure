;Responsible for input and output for playing the tic tac toe game

(ns -tictactoe.console.game_play_io
  (:require [-tictactoe.console.input_validation :as validation]
            [-tictactoe.console.message_writer :as writer]
            [-tictactoe.ttt.locale :as locale])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.convert_string_to_number :only (convert-string-to-number)]))

(def colors {:end-marker "\u001b[0m"
             :default "\u001b[39m"
             :white   "\u001b[37m"
             :black   "\u001b[30m"
             :red     "\u001b[31m"
             :green   "\u001b[32m"
             :blue    "\u001b[34m"
             :yellow  "\u001b[33m"
             :magenta "\u001b[35m"
             :cyan    "\u001b[36m"})

(defn display-current-player-marker [current-player-marker]
  (writer/write (translate (locale/loc) :output/current-player-marker current-player-marker)))

(defn colorize-markers [row player-one-marker]
  (map (fn [spot]
        (cond (number? spot) (str (get colors :yellow) spot (get colors :end-marker))
              (= player-one-marker spot) (str (get colors :red) spot (get colors :end-marker))
              :else (str (get colors :cyan) spot (get colors :end-marker))))
        row))

(defn print-row [row player-one-marker]
  (writer/write (clojure.string/join "\t" (colorize-markers row player-one-marker))))

(defn board-rows [board]
  (let [board-spaces (count board)
        board-dimension (-> board-spaces Math/sqrt int)
        spots (take board-spaces (iterate (partial + 1) 0))]
    (partition board-dimension (map #(nth board %) spots))))

(defn display-game-board [board player-one-marker]
  (writer/write "")
  (dorun (map #(print-row % player-one-marker) (board-rows board)))
  (writer/write ""))

(defn display-currently-registered-names [player-names]
  (if (> (count player-names) 0)
    (do (writer/write (translate (locale/loc) :output/player-names))
        (dorun (map #(writer/write %) player-names)))))

(defn get-player-one-name []
  (writer/write (translate (locale/loc) :input/player-one-name))
    (loop [player-one-name (read-line)]
      (cond (clojure.string/blank? player-one-name)
            (do
                (writer/write (translate (locale/loc) :error-messages/name-must-not-be-blank))
                (recur (read-line)))
          :else player-one-name)))

(defn get-player-two-name [player-one-name]
  (writer/write (translate (locale/loc) :input/player-two-name))
  (loop [player-two-name (read-line)]
    (cond (clojure.string/blank? player-two-name)
            (do
                (writer/write (translate (locale/loc) :error-messages/name-must-not-be-blank))
                (recur (read-line)))
          (= player-two-name player-one-name)
            (do
                (writer/write (translate (locale/loc) :error-messages/name-already-taken))
                (recur (read-line)))
        :else player-two-name)))

(defn get-player-one-marker []
  (writer/write (translate (locale/loc) :input/player-one-marker))
  (loop [player-one-marker (read-line)]
    (if (validation/marker-is-invalid player-one-marker)
        (do
          (writer/write (translate (locale/loc) :error-messages/bad-player-one-marker))
          (recur (read-line)))
        (clojure.string/upper-case player-one-marker))))

(defn get-player-two-marker [player-one-marker]
  (writer/write (translate (locale/loc) :input/player-two-marker))
  (loop [player-two-marker (read-line)]
    (if (or  (= player-two-marker player-one-marker)
             (validation/marker-is-invalid player-two-marker))
        (do
          (writer/write (translate (locale/loc) :error-messages/bad-player-two-marker))
          (recur (read-line)))
        (clojure.string/upper-case player-two-marker))))

(defn get-first-player [player-one-marker player-two-marker]
  (writer/write (translate (locale/loc) :input/player-going-first player-one-marker player-two-marker))
  (loop [first-player-marker (clojure.string/upper-case (read-line))]
    (if (validation/first-player-marker-is-invalid first-player-marker player-one-marker player-two-marker)
        (do
          (writer/write (translate (locale/loc) :error-messages/invalid-player-going-first player-one-marker player-two-marker))
          (recur (read-line)))
        first-player-marker)))

(defn ask-for-board-dimension []
  (writer/write (translate (locale/loc) :input/board-dimension))
  (loop [board-dimension (convert-string-to-number (read-line))]
    (if (validation/invalid-board-dimension board-dimension)
        (do
          (writer/write (translate (locale/loc) :error-messages/invalid-board-dimension))
          (recur (convert-string-to-number (read-line))))
        board-dimension)))

(defn yes-or-no-response []
  (loop [y-or-n (read-line)]
    (if (validation/y-or-n-response-is-invalid y-or-n (translate (locale/loc) :input/yes-option) (translate (locale/loc) :input/no-option))
        (do
             (writer/write (translate (locale/loc) :error-messages/bad-y-or-n-option))
             (recur (read-line)))
        (= y-or-n (translate (locale/loc) :input/yes-option)))))

(defn get-whether-player-one-is-ai []
  (writer/write (translate (locale/loc) :input/player-one-ai))
  (yes-or-no-response))

(defn get-whether-player-two-is-ai []
  (writer/write (translate (locale/loc) :input/player-two-ai))
  (yes-or-no-response))

(defn ask-if-player-wants-to-play-again-with-same-input []
  (writer/write (translate (locale/loc) :input/play-again-same-input))
  (yes-or-no-response))

(defn ask-if-player-wants-to-play-again-new-input []
  (writer/write (translate (locale/loc) :input/play-again-new-input))
  (yes-or-no-response))

(defn player-one-won-message []
  (writer/write (translate (locale/loc) :output/player-one-has-won)))

(defn player-two-won-message []
  (writer/write (translate (locale/loc) :output/player-two-has-won)))

(defn game-is-tied-message []
  (writer/write (translate (locale/loc) :output/game-has-been-tied)))

(defn end-game-message []
  (writer/write (translate (locale/loc) :output/end-game-message)))
