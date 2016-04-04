(ns -tictactoe.web.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.web.input_validation :refer :all]))

(describe "input-sanitation"

  (it "returns true when all parameters are valid"
    (let [params {:player-one-marker "X"
                  :player-two-marker "O"
                  :player-one-name "Bob"
                  :player-two-name "Jill"
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should (input-sanitation params))))

  (it "returns false when names are the same"
    (let [params {:player-one-marker "X"
                  :player-two-marker "O"
                  :player-one-name "Bob"
                  :player-two-name "Bob"
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false when markers are the same"
    (let [params {:player-one-marker "X"
                  :player-two-marker "X"
                  :player-one-name "Bob"
                  :player-two-name "Jill"
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false when both markers and names are same"
    (let [params {:player-one-marker "X"
                  :player-two-marker "X"
                  :player-one-name "Bob"
                  :player-two-name "Bob"
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false when one name is blank"
    (let [params {:player-one-marker "X"
                  :player-two-marker "O"
                  :player-one-name ""
                  :player-two-name "Bob"
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false when both names are blank"
    (let [params {:player-one-marker "X"
                  :player-two-marker "O"
                  :player-one-name ""
                  :player-two-name ""
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false when player one marker is not A-Z character"
    (let [params {:player-one-marker "<script> getAlert('FAIL');</script>"
                  :player-two-marker "O"
                  :player-one-name "Bob"
                  :player-two-name "Jane"
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false when player two marker is not A-Z character"
    (let [params {:player-one-marker "X"
                  :player-two-marker "<script> getAlert('FAIL');</script>"
                  :player-one-name "Bob"
                  :player-two-name "Jane"
                  :first-player "player-one-marker"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false when board is neither 3 or 4"
    (let [params {:player-one-marker "X"
                  :player-two-marker "Z"
                  :player-one-name "Bob"
                  :player-two-name "Jane"
                  :first-player "player-one-marker"
                  :board-dimension "12214124114251"}]
      (should-not (input-sanitation params))))

  (it "returns false when first player is not player-one-marker or player-two-marker"
    (let [params {:player-one-marker "X"
                  :player-two-marker "Z"
                  :player-one-name "Bob"
                  :player-two-name "Jane"
                  :first-player "FAIL"
                  :board-dimension "3"}]
      (should-not (input-sanitation params))))

  (it "returns false markers are neither A-Z or unique, names are both blank, first player is invalid and board is a word"
    (let [params {:player-one-marker "script attack"
                  :player-two-marker "script attack"
                  :player-one-name ""
                  :player-two-name ""
                  :first-player "FAIL"
                  :board-dimension "board"}]
      (should-not (input-sanitation params)))))

(describe "player-is-ai"

  (it "returns true when type is AI and dimension is 3"
    (let [type "AI"
          dimension "3"]
        (should (player-is-ai type dimension))))

  (it "returns false when type is AI and dimension is 4"
    (let [type "AI"
          dimension "4"]
        (should-not (player-is-ai type dimension))))

  (it "returns false when type is not AI and dimension is 3"
    (let [type "Human"
          dimension "3"]
        (should-not (player-is-ai type dimension))))

  (it "returns false when type is not AI and dimension is 4"
    (let [type "Human"
          dimension "4"]
        (should-not (player-is-ai type dimension)))))

(describe "spot-input-is-valid"

  (it "returns true when current player is a human and spot is open and a number"
    (let [board [0 1 2 3 4 5 6 7 8]
          current-player-is-ai false
          spot 4]
        (should (spot-input-is-valid board spot current-player-is-ai))))

  (it "returns false when current player is a human and spot is nil"
    (let [board [0 1 2 3 4 5 6 7 8]
          current-player-is-ai false
          spot nil]
        (should-not (spot-input-is-valid board spot current-player-is-ai))))

  (it "returns false when current player is a human and spot is already marked"
    (let [board [0 1 2 3 "X" 5 6 7 8]
          current-player-is-ai false
          spot 4]
        (should-not (spot-input-is-valid board spot current-player-is-ai))))

  (it "returns false when current player is a human and spot is not a number"
    (let [board [0 1 2 3 "X" 5 6 7 8]
          current-player-is-ai false
          spot "script hacks"]
        (should-not (spot-input-is-valid board spot current-player-is-ai))))

  (it "returns false when current player is an ai and spot is not nil"
    (let [board [0 1 2 3 "X" 5 6 7 8]
          current-player-is-ai true
          spot "garble"]
        (should-not (spot-input-is-valid board spot current-player-is-ai))))

  (it "returns true when current player is an ai and spot is nil"
    (let [board [0 1 2 3 "X" 5 6 7 8]
          current-player-is-ai true
          spot nil]
        (should (spot-input-is-valid board spot current-player-is-ai)))))
