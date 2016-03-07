(ns -tictactoe.console_io
  (:require [-tictactoe.input_validation :refer :all]))

(defn start-game-message []
  (println "Welcome to the Tic Tac Toe Game!"))

(defn display-current-player-marker [current-player-marker]
  (println "\nCurrent Player Marker:" current-player-marker))

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

(defn colorize-markers [row]
  (map (fn [spot]
        (if (number? spot) (str (get colors :cyan) spot (get colors :end-marker))
            (str (get colors :red) spot (get colors :end-marker))))
        row))

(defn print-row [row]
  (println (clojure.string/join "\t" (colorize-markers row))))

(defn display-game-board-3x3 [board]
  (println)
  (print-row (subvec board 0 3))
  (print-row (subvec board 3 6))
  (print-row (subvec board 6 9))
  (println))

(defn display-game-board-4x4 [board]
  (println)
  (print-row (subvec board 0 4))
  (print-row (subvec board 4 8))
  (print-row (subvec board 8 12))
  (print-row (subvec board 12 16))
  (println))

(defn display-game-board [board]
  (cond (= (count board) 9) (display-game-board-3x3 board)
        (= (count board) 16) (display-game-board-4x4 board)))

(defn player-one-won-message []
  (println "\nPlayer One Won!"))

(defn player-two-won-message []
  (println "\nPlayer Two Won!"))

(defn game-is-tied-message []
  (println "\nTied!"))

(defn end-game-message []
  (println "\nThank you for playing!"))

(defn get-player-spot-to-be-marked [board]
  (println "Please input spot to be marked. Must be 0 -" (dec (count board)) "and open.")
  (loop [spot (convert-string-to-number (read-line))
         board board]
    (if (check-if-spot-is-invalid board spot)
      (do (cond (check-if-spot-is-invalid-input board spot) (println "Spot must be an integer corresponding to an open location.")
                (check-if-spot-is-not-on-board board spot) (println "Spot must be a location on the game board.")
                (check-if-spot-is-already-marked board spot) (println "Spot must be open and unmarked.")
                :else (println "That spot is not valid."))
          (recur (convert-string-to-number (read-line)) board))
      spot)))

(defn get-player-one-name []
  (println "Please input player one's name.")
    (loop [player-one-name (read-line)]
      (if (clojure.string/blank? player-one-name)
          (do
            (println "Name must not be blank.")
            (recur (read-line)))
          player-one-name)))

(defn get-player-two-name [player-one-name]
  (println "Please input player two's name.")
  (loop [player-two-name (read-line)]
    (if (or (= player-two-name player-one-name)
            (clojure.string/blank? player-two-name))
        (do
          (println "Player names cannot be identical or blank.")
          (recur (read-line)))
        player-two-name)))

(defn get-player-one-marker []
  (println "Please input player one's marker. Marker must be a single character long and not a number.")
  (loop [player-one-marker (read-line)]
    (if (check-if-marker-is-invalid player-one-marker)
        (do
          (println "Marker must be a single character and not a number.")
          (recur (read-line)))
        (clojure.string/upper-case player-one-marker))))

(defn get-player-two-marker [player-one-marker]
  (println "Please input player two's marker. Marker must be a single character long, not a number, and different from player one's marker.")
  (loop [player-two-marker (read-line)]
    (if (or  (= player-two-marker player-one-marker)
             (check-if-marker-is-invalid player-two-marker))
        (do
          (println "Marker must be unique, single length, and not a number.")
          (recur (read-line)))
        (clojure.string/upper-case player-two-marker))))

(defn get-first-player [player-one-marker player-two-marker]
  (println "Please input marker of player going first. Either:" player-one-marker "or" player-two-marker)
  (loop [first-player-marker (read-line)]
    (if (check-if-first-player-marker-is-invalid first-player-marker player-one-marker player-two-marker)
        (do
          (println "Please input one of the already defined markers. Either:" player-one-marker "or" player-two-marker)
          (recur (read-line)))
        first-player-marker)))

(defn ask-for-either-3x3-or-4x4-board []
  (println "Choose either 3x3 or 4x4 board. Type 3 for 3x3 and 4 for 4x4.")
  (loop [board-type (read-line)]
    (if (and (not (= board-type "3"))
                 (not (= board-type "4")))
        (do
          (println "Type 3 for 3x3 and 4 for 4x4.")
          (recur (read-line)))
        (convert-string-to-number board-type))))

(defn check-if-yes-or-no-response-is-invalid [response]
  (and (not (= response "y"))
       (not (= response "n"))))

(defn get-whether-player-one-is-ai []
  (println "Input y/n for player one being an AI.")
  (loop [one-ai (read-line)]
    (if (check-if-yes-or-no-response-is-invalid one-ai)
        (do
             (println "Please input y if you would like player one to be an AI or n for a human player")
             (recur (read-line)))
        (= one-ai "y"))))

(defn get-whether-player-two-is-ai []
  (println "Input y/n for player two being an AI.")
  (loop [two-ai (read-line)]
    (if (check-if-yes-or-no-response-is-invalid two-ai)
        (do
             (println "Please input y if you would like player two to be an AI or n for a human player")
             (recur (read-line)))
        (= two-ai "y"))))

(defn ask-if-player-wants-to-play-again-with-same-input []
  (println "\nPlay again with same input? (y/n)")
  (loop [play-again (read-line)]
    (if (check-if-yes-or-no-response-is-invalid play-again)
        (do
             (println "Please input either y or n.")
             (recur (read-line)))
        (= play-again "y"))))

(defn ask-if-player-wants-to-play-again []
  (println "\nPlay again with new input? (y/n)")
  (loop [play-again (read-line)]
    (if (check-if-yes-or-no-response-is-invalid play-again)
        (do
             (println "Please input either y or n.")
             (recur (read-line)))
        (= play-again "y"))))

(defn display-player-scores [player-scores]
  (println "\nPlayer Scores:")
  (doseq [[player-name score] player-scores]
        (println (str player-name ": " score))))

(defn select-menu-option [menu-options]
  (println "\nMenu:")
  (doseq [[menu-number, menu-option] menu-options]
        (println (str menu-number ". " menu-option)))
  (println "Please select option by typing in number.")
  (loop [option (convert-string-to-number (read-line))]
        (if (and (number? option)
                  (<= option (count menu-options))
                  (> option 0))
                  option
                  (do (println "Option must be a number matched with one of the available options.")
                    (recur (convert-string-to-number (read-line)))))))
