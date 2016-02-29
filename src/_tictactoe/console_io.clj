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
        (recur (read-line) board))
      spot)
  )
)


(defn display-game-board [board]
  (println board)
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

(defn player_one_won_message
  "Displays message when player one has won"
  []
  (println "Player One Won!"))

(defn player_two_won_message
  "Displays message when player two has won"
  []
  (println "Player Two Won!"))

(defn game_tied_message
  "Displays message when game has been tied"
  []
  (println "Tied!"))
