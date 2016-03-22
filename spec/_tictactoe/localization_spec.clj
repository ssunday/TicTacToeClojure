(ns -tictactoe.localization-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.localization :refer :all]))

(describe "localize"
  (around [it]
    (with-out-str (it)))

  (it "returns English welcome message by default"
    (should= "Welcome to the Tic Tac Toe Game!" (translate :en :output/welcome-message)))

  (it "returns German welcome message if de is passed in"
    (should= "Willkommen in der Tic Tac Toe-Spiel!" (translate :de :output/welcome-message)))

  (it "returns Missing translation message if message does not exist"
    (should= "Translation missing" (translate :en :fake-message))))
