(ns -tictactoe.console_io)

(defn start_game_message
  "Displays message when game is started."
  []
  (println "Welcome to the Tic Tac Toe Game!"))

(defn end_game_message
  "Displays message when game has been ended."
  []
  (println "Thank you for playing!"))

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
