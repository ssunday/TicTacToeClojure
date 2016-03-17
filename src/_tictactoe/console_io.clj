(ns -tictactoe.console_io
  (:require [-tictactoe.input_validation :as validation])
  (:use [-tictactoe.localization :only (translate)]))

(def loc (or (keyword (System/getenv "LOC"))
              :en))

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

(defn start-game-message []
  (println (translate loc :output/welcome-message)))

(defn display-current-player-marker [current-player-marker]
  (println (translate loc :output/current-player-marker current-player-marker)))

(defn colorize-markers [row player-one-marker]
  (map (fn [spot]
        (cond (number? spot) (str (get colors :yellow) spot (get colors :end-marker))
              (= player-one-marker spot) (str (get colors :red) spot (get colors :end-marker))
              :else (str (get colors :cyan) spot (get colors :end-marker))))
        row))

(defn print-row [row player-one-marker]
  (println (clojure.string/join "\t" (colorize-markers row player-one-marker))))

(defn board-rows [board]
  (let [board-spaces (count board)
        board-dimension (-> board-spaces Math/sqrt int)
        spots (take board-spaces (iterate (partial + 1) 0))]
    (partition board-dimension (map #(nth board %) spots))))

(defn display-game-board [board player-one-marker]
  (println)
  (dorun (map #(print-row % player-one-marker) (board-rows board)))
  (println))

(defn get-player-spot-to-be-marked [board]
  (println (translate loc :input/spot-to-be-marked (dec (count board))))
  (loop [spot (validation/convert-string-to-number (read-line))]
    (if (validation/spot-is-invalid board spot)
      (do (cond (validation/spot-is-invalid-input spot) (println (translate loc :error-messages/spot-is-invalid-input))
                (validation/spot-is-not-on-board board spot) (println (translate loc :error-messages/spot-is-not-on-board))
                (validation/spot-is-already-marked board spot) (println (translate loc :error-messages/spot-not-open)))
          (recur (validation/convert-string-to-number (read-line))))
      spot)))

(defn display-currently-registered-names [player-names]
  (if (> (count player-names) 0)
    (do (println (translate loc :output/player-names))
        (dorun (map #(println %) player-names)))))

(defn get-player-one-name []
  (println (translate loc :input/player-one-name))
    (loop [player-one-name (read-line)]
      (cond (clojure.string/blank? player-one-name)
            (do
                (println (translate loc :error-messages/name-must-not-be-blank))
                (recur (read-line)))
          :else player-one-name)))

(defn get-player-two-name [player-one-name]
  (println (translate loc :input/player-two-name))
  (loop [player-two-name (read-line)]
    (cond (clojure.string/blank? player-two-name)
            (do
                (println (translate loc :error-messages/name-must-not-be-blank))
                (recur (read-line)))
          (= player-two-name player-one-name)
            (do
                (println (translate loc :error-messages/name-already-taken))
                (recur (read-line)))
        :else player-two-name)))

(defn get-player-one-marker []
  (println (translate loc :input/player-one-marker))
  (loop [player-one-marker (read-line)]
    (if (validation/marker-is-invalid player-one-marker)
        (do
          (println (translate loc :error-messages/bad-player-one-marker))
          (recur (read-line)))
        (clojure.string/upper-case player-one-marker))))

(defn get-player-two-marker [player-one-marker]
  (println (translate loc :input/player-two-marker))
  (loop [player-two-marker (read-line)]
    (if (or  (= player-two-marker player-one-marker)
             (validation/marker-is-invalid player-two-marker))
        (do
          (println (translate loc :error-messages/bad-player-two-marker))
          (recur (read-line)))
        (clojure.string/upper-case player-two-marker))))

(defn get-first-player [player-one-marker player-two-marker]
  (println (translate loc :input/player-going-first player-one-marker player-two-marker))
  (loop [first-player-marker (clojure.string/upper-case (read-line))]
    (if (validation/first-player-marker-is-invalid first-player-marker player-one-marker player-two-marker)
        (do
          (println (translate loc :error-messages/invalid-player-going-first player-one-marker player-two-marker))
          (recur (read-line)))
        first-player-marker)))

(defn ask-for-board-dimension []
  (println (translate loc :input/board-dimension))
  (loop [board-dimension (read-line)]
    (if (validation/invalid-board-dimension board-dimension)
        (do
          (println (translate loc :error-messages/invalid-board-dimension))
          (recur (read-line)))
        (validation/convert-string-to-number board-dimension))))

(defn yes-or-no-response []
  (loop [y-or-n (read-line)]
    (if (validation/y-or-n-response-is-invalid y-or-n (translate loc :input/yes-option) (translate loc :input/no-option))
        (do
             (println (translate loc :error-messages/bad-y-or-n-option))
             (recur (read-line)))
        (= y-or-n (translate loc :input/yes-option)))))

(defn get-whether-player-one-is-ai []
  (println (translate loc :input/player-one-ai))
  (yes-or-no-response))

(defn get-whether-player-two-is-ai []
  (println (translate loc :input/player-two-ai))
  (yes-or-no-response))

(defn ask-if-player-wants-to-play-again-with-same-input []
  (println (translate loc :input/play-again-same-input))
  (yes-or-no-response))

(defn ask-if-player-wants-to-play-again-new-input []
  (println (translate loc :input/play-again-new-input))
  (yes-or-no-response))

(defn player-one-won-message []
  (println (translate loc :output/player-one-has-won)))

(defn player-two-won-message []
  (println (translate loc :output/player-two-has-won)))

(defn game-is-tied-message []
  (println (translate loc :output/game-has-been-tied)))

(defn end-game-message []
  (println (translate loc :output/end-game-message)))

(defn display-tally [tally]
  (println (translate loc :output/player-tally))
  (doseq [[player-name scores] tally]
        (println (str player-name ":\n" (translate loc :output/tally-header) ":\t"(scores :wins) "/" (scores :losses) "/" (scores :draws)))))

(defn select-menu-option [menu-options]
  (println "\n" (translate loc :menu/menu))
  (doseq [[menu-number, menu-option] menu-options]
        (println (str menu-number ". " (translate loc (keyword "menu" menu-option)))))
  (println (translate loc :menu/select-menu-option))
  (loop [option (validation/convert-string-to-number (read-line))]
        (if (validation/menu-option-is-valid (count menu-options) option)
            option
            (do (println (translate loc :error-messages/invalid-menu-option))
                (recur (validation/convert-string-to-number (read-line)))))))
