(ns -tictactoe.game_play_io-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.game_play_io :refer :all]))

(describe "get-player-one-name"
  (around [it]
    (with-out-str (it)))

  (it "returns name if not blank"
    (let [name "Sarah"]
      (should= name
        (with-in-str name
         (get-player-one-name))))))

(describe "get-player-two-name"
  (around [it]
    (with-out-str (it)))

  (it "returns name if not blank or identical to player one name"
    (let [player-one "Sarah"
          player-two "John"]
      (should= player-two
        (with-in-str player-two
         (get-player-two-name player-one))))))

(describe "get-player-one-marker"
  (around [it]
    (with-out-str (it)))

  (it "returns uppercase version of lowercase input"
    (let [marker "a"]
      (should= (clojure.string/upper-case marker)
        (with-in-str marker
         (get-player-one-marker)))))

  (it "returns exact version of uppercase input"
    (let [marker "A"]
      (should= marker
        (with-in-str marker
         (get-player-one-marker))))))

(describe "ask-for-board-dimension"
  (around [it]
    (with-out-str (it)))

  (it "returns integer dimension for 3x3 board"
    (let [dimension 3]
      (should= dimension
        (with-in-str (str dimension)
         (ask-for-board-dimension)))))

  (it "returns integer dimension for 4x4 board"
    (let [dimension 4]
      (should= dimension
        (with-in-str (str dimension)
         (ask-for-board-dimension))))))

(describe "yes-or-no-response"

  (around [it]
    (with-out-str (it)))

  (it "returns true if response is y"
    (let [response "y"]
      (should (with-in-str response
         (yes-or-no-response)))))

  (it "returns false if response is n"
    (let [response "n"]
      (should-not (with-in-str response
         (yes-or-no-response))))))
