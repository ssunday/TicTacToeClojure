(ns -tictactoe.console_io)

(defn start-game-message []
  (println "Welcome to the Tic Tac Toe Game!"))

(defn end-game-message []
  (println "Thank you for playing!"))

(defn convert_string_to_number [str]
  (let [spot (read-string str)]
       (if (number? spot) spot nil)))

(defn get-player-spot-to-be-marked [board]
  (println "Please input spot to be marked. Must be 0-8 and available.")
  (loop [spot (convert_string_to_number (read-line))
        board board]
    (if (nil? (find board spot))
      (do
        (println "Spot must be open.")
        (recur (convert_string_to_number (read-line)) board))
      spot)
  )
)


(defn display-game-board [board]
  (apply println (subvec board 0 3))
  (apply println (subvec board 3 6))
  (apply println (subvec board 6 9))
)

(defn get-player-one-marker []
  (println "Please input player one's marker. Marker must be a single character long and not a number.")
  (read-line)
)

(defn get-player-two-marker [player-one-marker]
  (println "Please input player two's marker. Marker must be a single character long, not a number, and different from player one's marker.")
  (loop [player-two-marker (read-line)
        player-one-marker player-one-marker]
    (if (= player-two-marker player-one-marker)
        (do
          (println "Marker must be unique.")
          (recur (read-line) player-one-marker))
        player-two-marker)))

(defn player-one-won-message []
  (println "Player One Won!"))

(defn player-two-won-message []
  (println "Player Two Won!"))

(defn game-is-tied-message []
  (println "Tied!"))
