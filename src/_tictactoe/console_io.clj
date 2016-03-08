(ns -tictactoe.console_io
  (:require [-tictactoe.input_validation :refer :all])
  (:use [-tictactoe.localization :only (translate)]))

(def loc (or (keyword (System/getenv "LOC"))
              :en))

(defn start-game-message []
  (println (translate loc :output/welcome-message)))

(defn display-current-player-marker [current-player-marker]
  (println (translate loc :output/current-player-marker current-player-marker)))

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

(defn colorize-markers [row player-one-marker]
  (map (fn [spot]
        (cond (number? spot) (str (get colors :cyan) spot (get colors :end-marker))
              (= player-one-marker spot) (str (get colors :red) spot (get colors :end-marker))
              :else (str (get colors :blue) spot (get colors :end-marker))))
        row))

(defn print-row [row player-one-marker]
  (println (clojure.string/join "\t" (colorize-markers row player-one-marker))))

(defn display-game-board-3x3 [board player-one-marker]
  (println)
  (print-row (subvec board 0 3) player-one-marker)
  (print-row (subvec board 3 6) player-one-marker)
  (print-row (subvec board 6 9) player-one-marker)
  (println))

(defn display-game-board-4x4 [board player-one-marker]
  (println)
  (print-row (subvec board 0 4) player-one-marker)
  (print-row (subvec board 4 8) player-one-marker)
  (print-row (subvec board 8 12) player-one-marker)
  (print-row (subvec board 12 16) player-one-marker)
  (println))

(defn display-game-board [board player-one-marker]
  (cond (= (count board) 9) (display-game-board-3x3 board player-one-marker)
        (= (count board) 16) (display-game-board-4x4 board player-one-marker)))

(defn get-player-spot-to-be-marked [board]
  (println (translate loc :input/spot-to-be-marked (dec (count board))))
  (loop [spot (convert-string-to-number (read-line))
         board board]
    (if (check-if-spot-is-invalid board spot)
      (do (cond (check-if-spot-is-invalid-input board spot) (println (translate loc :error-messages/spot-is-invalid-input))
                (check-if-spot-is-not-on-board board spot) (println (translate loc :error-messages/spot-is-not-on-board))
                (check-if-spot-is-already-marked board spot) (println (translate loc :error-messages/spot-not-open)))
          (recur (convert-string-to-number (read-line)) board))
      spot)))

(defn get-player-one-name []
  (println (translate loc :input/player-one-name))
    (loop [player-one-name (read-line)]
      (if (clojure.string/blank? player-one-name)
          (do
            (println (translate loc :error-messages/name-must-not-be-blank))
            (recur (read-line)))
          player-one-name)))

(defn get-player-two-name [player-one-name]
  (println (translate loc :input/player-two-name))
  (loop [player-two-name (read-line)]
    (if (or (= player-two-name player-one-name)
            (clojure.string/blank? player-two-name))
        (do
          (println (translate loc :error-messages/name-must-not-be-blank-or-identical))
          (recur (read-line)))
        player-two-name)))

(defn get-player-one-marker []
  (println (translate loc :input/player-one-marker))
  (loop [player-one-marker (read-line)]
    (if (check-if-marker-is-invalid player-one-marker)
        (do
          (println (translate loc :error-messages/bad-player-one-marker))
          (recur (read-line)))
        (clojure.string/upper-case player-one-marker))))

(defn get-player-two-marker [player-one-marker]
  (println (translate loc :input/player-two-marker))
  (loop [player-two-marker (read-line)]
    (if (or  (= player-two-marker player-one-marker)
             (check-if-marker-is-invalid player-two-marker))
        (do
          (println (translate loc :error-messages/bad-player-two-marker))
          (recur (read-line)))
        (clojure.string/upper-case player-two-marker))))

(defn get-first-player [player-one-marker player-two-marker]
  (println (translate loc :input/player-going-first player-one-marker player-two-marker))
  (loop [first-player-marker (clojure.string/upper-case (read-line))]
    (if (check-if-first-player-marker-is-invalid first-player-marker player-one-marker player-two-marker)
        (do
          (println (translate loc :error-messages/invalid-player-going-first player-one-marker player-two-marker))
          (recur (read-line)))
        first-player-marker)))

(defn ask-for-either-3x3-or-4x4-board []
  (println (translate loc :input/board-dimension))
  (loop [board-type (read-line)]
    (if (and (not (= board-type "3"))
                 (not (= board-type "4")))
        (do
          (println (translate loc :error-messages/invalid-board-dimension))
          (recur (read-line)))
        (convert-string-to-number board-type))))

(defn check-if-yes-or-no-response-is-invalid [response]
  (and (not (= response "y"))
       (not (= response "n"))))

(defn get-whether-player-one-is-ai []
  (println (translate loc :input/player-one-ai))
  (loop [one-ai (read-line)]
    (if (check-if-yes-or-no-response-is-invalid one-ai)
        (do
             (println (translate loc :error-messages/bad-y-or-n-option))
             (recur (read-line)))
        (= one-ai "y"))))

(defn get-whether-player-two-is-ai []
  (println (translate loc :input/player-two-ai))
  (loop [two-ai (read-line)]
    (if (check-if-yes-or-no-response-is-invalid two-ai)
        (do
             (println (translate loc :error-messages/bad-y-or-n-option))
             (recur (read-line)))
        (= two-ai "y"))))

(defn ask-if-player-wants-to-play-again-with-same-input []
  (println (translate loc :input/play-again-same-input))
  (loop [play-again (read-line)]
    (if (check-if-yes-or-no-response-is-invalid play-again)
        (do
             (println (translate loc :error-messages/bad-y-or-n-option))
             (recur (read-line)))
        (= play-again "y"))))

(defn ask-if-player-wants-to-play-again []
  (println (translate loc :input/play-again-new-input))
  (loop [play-again (read-line)]
    (if (check-if-yes-or-no-response-is-invalid play-again)
        (do
             (println (translate loc :error-messages/bad-y-or-n-option))
             (recur (read-line)))
        (= play-again "y"))))

(defn player-one-won-message []
  (println (translate loc :output/player-one-has-won)))

(defn player-two-won-message []
  (println (translate loc :output/player-two-has-won)))

(defn game-is-tied-message []
  (println (translate loc :output/game-has-been-tied)))

(defn end-game-message []
  (println (translate loc :output/end-game-message)))

(defn display-player-scores [player-scores]
  (println (translate loc :output/player-scores))
  (doseq [[player-name score] player-scores]
        (println (str player-name ": " score))))

(defn select-menu-option [menu-options]
  (println (translate loc :menu/menu))
  (doseq [[menu-number, menu-option] menu-options]
        (println (str menu-number ". " (translate loc (keyword "menu" menu-option)))))
  (println (translate loc :menu/select-menu-option))
  (loop [option (convert-string-to-number (read-line))]
        (if (and (number? option)
                  (<= option (count menu-options))
                  (> option 0))
                  option
                  (do (println (translate loc :error-messages/invalid-menu-option))
                    (recur (convert-string-to-number (read-line)))))))
