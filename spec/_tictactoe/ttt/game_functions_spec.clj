(ns -tictactoe.ttt.game_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.game_functions :refer :all]))

(def pl "X")

(def op "O")

(describe "make-default-board"

  (it "The default board for a 3x3 is a vector of 0-8."
    (should= [0 1 2 3 4 5 6 7 8] (make-default-board 3))

  (it "The default board for a 4x4 is a vector of 0-15."
    (should= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] (make-default-board 4)))))

(describe "mark-board-location"

  (it "correctly marks 3x3 board given spot and marker."
    (should= [0 1 2 pl 4 5 6 7 8] (mark-board-location (make-default-board 3) 3 pl))

  (it "correctly marks 4x4 board given spot and marker."
    (should= [0 1 2 pl 4 5 6 7 8 9 10 11 12 13 14 15] (mark-board-location (make-default-board 3) 3 pl)))))

(describe "winning-combinations"

  (it "returns a vector of all possible winning row combinations of the current 3x3 board"
    (let [board [0 1 pl
                 pl 4 op
                 6 7 op]]
      (should= [[0 1 pl] [pl 4 op] [6 7 op] [0 pl 6] [1 4 7] [pl op op] [0 4 op] [pl 4 6]]
               (winning-combinations board))))

  (it "returns a vector of all possible winning row combinations of the current 4x4 board"
    (let [board [0 1 pl 3
                 pl 5 op pl
                 8 9 10 11
                 12 op 14 pl]]
      (should= [[0 1 pl 3] [pl 5 op pl] [8 9 10 11] [12 op 14 pl]
                [0 pl 8 12] [1 5 9 op] [pl op 10 14]  [3 pl 11 pl]
                [0 5 10 pl] [3 op 9 12]]
               (winning-combinations board)))))

(describe "game-is-won"

  (context "Game is won with horizontal match returns winning marker"

    (it "for 3x3 board middle row"
      (should= pl (game-is-won [0 1 2 pl pl pl 6 7 8])))

    (it "for 4x4 board top row"
      (should= pl  (game-is-won [pl pl pl pl 4 5 6 7 8 9 10 11 12 13 14 15]))))

  (context "Game is won with vertical match returns winning marker "

    (it "for 3x3 board middle column"
      (should= pl (game-is-won [0 pl 2
                                3 pl 5
                                6 pl 8])))

    (it "for 4x4 board last column"
      (should= pl (game-is-won [0 1 2 pl
                                4 5 6 pl
                                8 9 10 pl
                                12 13 14 pl]))))

  (context "Game is won with diagonal match returns winning marker"

    (it "for 3x3 board TL-BR"
       (should= pl (game-is-won [pl 1 2
                                 3 pl 5
                                 6 7 pl])))

    (it "for 4x4 board TR - BL"
       (should= pl (game-is-won [0 1 2 pl
                                 4 5 pl 7
                                 8 pl 10 11
                                 pl 13 14 15])))

    (it "for 3x3 board TR-BL"
       (should= pl (game-is-won [0 1 pl
                                 3 pl 5
                                 pl 7 8])))

    (it "for 4x4 board TR - BL"
       (should= pl (game-is-won [pl 1 2 pl
                                 4 pl 6 7
                                 8 9 pl 11
                                 12 13 14 pl]))))

  (context "game has not been won returns nil"

    (it "for 3x3 board"
     (should= nil (game-is-won [0 pl 2
                                op pl 5
                                6 op 8])))
    (it "for 4x4 board"
     (should= nil (game-is-won [op 1 2 pl
                                4 5 pl 7
                                8 pl 10 op
                                op 13 14 15]))))

  (context "game has been tied returns nil"
    (it "for 3x3 board"
        (should= nil  (game-is-won [op pl op
                                    op pl pl
                                    pl op op]))
    (it "for 4x4 board"
        (should= nil  (game-is-won [op op pl pl
                                    op pl op op
                                    pl op op pl
                                    pl op op pl]))))))

(describe "game-is-tied"

  (context "game is tied"
    (it "returns true for 3x3 board"
      (game-is-tied [op pl op
                     op pl pl
                     pl op op]))

    (it "returns true for 4x4 board"
      (game-is-tied [op op pl pl
                     op pl op op
                     pl op op pl
                     pl op op pl])))

  (context "game has been won"
    (it "returns false for 3x3 board"
        (not (game-is-tied [op pl op
                            op pl pl
                            op op pl])))
    (it "returns false for 4x4 board"
         (not (game-is-tied [0 1 2 pl
                            4 5 pl 7
                            8 pl 10 11
                            pl 13 14 15])))))

(describe "game-is-over"
  (it "returns true when game has been tied for 3x3 board"
      (game-is-over [op pl op
                            op pl pl
                            pl op op]))

  (it "returns true when game has been tied for 4x4 board"
      (game-is-over [op op pl pl
                           op pl op op
                           pl op op pl
                           pl op op pl]))

  (it "returns true when game has been won for 3x3 board"
      (game-is-over [op pl op
                            op pl pl
                            op op pl]))

  (it "returns true when game has been won for 4x4 board"
      (game-is-over [op op pl pl
                            op pl pl op
                            op op pl pl
                            op pl op pl]))

  (it "returns false when game neither been won or tied for 3x3 board"
     (not (game-is-over [op pl 2
                                3 pl 5
                                op 7 pl])))

  (it "returns false when game neither been won or tied for 4x4 board"
     (not (game-is-over [op 1 2 pl
                                4 5 pl 7
                                8 pl 10 op
                                op 13 14 15]))))
