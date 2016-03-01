(ns -tictactoe.console_io)

(defn convert-string-to-number [str]
  (try
    (let [spot (read-string str)]
       (if (number? spot) spot nil))
    (catch Exception e nil)))

(defn check-if-marker-is-invalid [marker]
  (or (> (count marker) 1)
      (clojure.string/blank? marker)
      (not (nil? (convert-string-to-number marker)))))

(defn check-if-yes-or-no-response-is-invalid [response]
  (and (not (= response "y"))
       (not (= response "n"))))

(defn start-game-message []
  (println "Welcome to the Tic Tac Toe Game!"))

(defn end-game-message []
  (println "Thank you for playing!"))

(defn get-player-spot-to-be-marked [board]
  (println "Please input spot to be marked. Must be 0-8 and open.")
  (loop [spot (convert-string-to-number (read-line))
        board board]
    (if (or (nil? (find board spot))
            (not  (number? (get board spot))))
      (do
        (println "Spot must be open.")
        (recur (convert-string-to-number (read-line)) board))
      spot)))

(defn display-game-board [board]
  (println)
  (apply println (subvec board 0 3))
  (apply println (subvec board 3 6))
  (apply println (subvec board 6 9))
  (println)
)

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
  (loop [player-two-marker (read-line)
        player-one-marker player-one-marker]
    (if (or  (= player-two-marker player-one-marker)
             (check-if-marker-is-invalid player-two-marker))
        (do
          (println "Marker must be unique, single length, and not a number.")
          (recur (read-line) player-one-marker))
        (clojure.string/upper-case player-two-marker))))

(defn get-first-player [player-one-marker player-two-marker]
  (println "Please input marker of player going first. Either:" player-one-marker "or" player-two-marker)
  (loop [first-player-marker (read-line)]
    (if (and (not (= first-player-marker player-one-marker))
             (not (= first-player-marker player-two-marker)))
          (do
            (println "Please input one of the already defined markers. Either:" player-one-marker "or" player-two-marker)
            (recur (read-line)))
          first-player-marker)))

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

(defn display-current-player-marker [current-player-marker]
  (println "\nCurrent Player Marker:" current-player-marker))

(defn player-one-won-message []
  (println "\nPlayer One Won!"))

(defn player-two-won-message []
  (println "\nPlayer Two Won!"))

(defn game-is-tied-message []
  (println "\nTied!"))

(defn ask-if-player-wants-to-play-again []
  (println "\nPlay Again? (y/n)")
  (loop [play-again (read-line)]
    (if (check-if-yes-or-no-response-is-invalid play-again)
        (do
             (println "Please input either y or n.")
             (recur (read-line)))
        (= play-again "y"))))
