(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(def ai "X")

(def pl "O")

(describe "get-available-locations"
  (it "returns only numbered, open spots for partially marked 3x3 board"
    (should= [0 3 4 5] (get-available-locations [0 ai pl 3 4 5 ai ai pl])))

  (it "returns entire 3x3 board when nothing has been marked"
    (should= [0 1 2 3 4 5 6 7 8] (get-available-locations [0 1 2 3 4 5 6 7 8])))

  (it "returns entire 4x4 board when nothing has been marked"
    (should= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] (get-available-locations [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])))

  (it "returns only numbered, open spots for partially marked 4x4 board"
    (should= [0 3 4 5 7 8 10 11 15] (get-available-locations [0 ai pl 3
                                                   4 5 pl 7
                                                   8 ai 10 11
                                                  ai pl pl 15]))))

(describe "player-markers"

  (it "assigns first marker to keyword ai in a hash"
    (let [markers (player-markers ai pl)]
      (should= ai (:ai markers))))

  (it "assigns second marker to keyword opponent in a hash"
    (let [markers (player-markers ai pl)]
      (should= pl (:opponent markers)))))

(describe "get-score"
    (def markers {:ai ai :opponent pl})

    (it "returns 0 for a tied board of depth 0"
      (should= 0 (get-score [pl ai ai
                         ai pl pl
                         pl ai ai] markers 0)))

    (it "returns 0 for a tied board of depth 5"
      (should= 0 (get-score [pl ai ai
                         ai pl pl
                         pl ai ai] markers 5)))

    (it "returns 10 for a won board by ai previous turn of depth 0"
      (should= 100 (get-score [ai ai ai
                         ai pl pl
                         pl ai pl] markers 0)))

    (it "returns 9 for a won board by ai of depth 1"
      (should= 99 (get-score [ai ai ai
                         ai pl pl
                         pl ai pl] markers 1)))

    (it "returns -99 for a won board by other player of depth 1"
      (should= -99 (get-score [pl pl pl
                         ai ai pl
                         pl ai ai] markers 1)))

    (it "returns -95 for a won board by other player of depth 5"
      (should= -95 (get-score [pl pl pl
                         ai ai pl
                         pl ai ai] markers 5))))

(describe "ai-move"

  (it "returns top left corner spot on empty board"
    (should= 8 (best-move [0 1 2 3 4 5 6 7 8] ai pl)))

  (it "returns middle spot if top right corner is occupied"
    (should= 4 (best-move [0 1 pl
                          3 4 5
                          6 7 8] ai pl)))

  (it "returns middle spot if top left corner is occupied"
    (should= 4 (best-move [pl 1 2
                          3 4 5
                          6 7 8] ai pl)))

  (it "returns middle spot if bottom left corner is occupied"
    (should= 4 (best-move [0 1 2
                          3 4 5
                          pl 7 8] ai pl)))

  (it "returns middle spot if bottom right corner is occupied"
    (should= 4 (best-move [0 1 2
                          3 4 5
                          6 7 pl] ai pl)))

  (it "takes right middle spot when opponent has 2 in a row in that row"
    (should= 5 (best-move [ai ai pl
                            pl pl 5
                            ai 7 8] ai pl)))

  (it "returns middle spot when top right is marked by opponent"
    (should= 4 (best-move [0 1 pl
                            3 4 5
                            6 7 8] ai pl)))

  (it "blocks an opponent from winning on top row horizontal"
    (should= 2 (best-move [pl pl 2
                            3 ai 5
                            6 7 8] ai pl)))

  (it "blocks an opponent from winning in the third column"
    (should= 8 (best-move [0 1 pl
                            3 ai pl
                            6 7 8] ai pl)))

  (it "blocks an opponent from winning in the top row"
    (should= 1 (best-move [pl 1 pl
                            3 ai pl
                            6 7 ai] ai pl)))

  (it "blocks opponent from winning in diagonal case TR-BL"
    (should= 6 (best-move [ai ai pl
                            3 pl 5
                            6  7  8] ai pl)))

  (it "returns winning spot on board where it has two in a row in the first column"
    (should= 6 (best-move [ai pl pl
                          ai 4 5
                          6 7 8] ai pl)))

  (it "wins in third column instead of blocking in middle column."
    (should= 5 (best-move [ai pl ai
                           ai pl 5
                           pl 7 ai] ai pl)))

  (it "wins when it has the opportunity"
    (should= 8 (best-move [ai pl pl
                            3 ai 5
                            6 7 8] ai pl)))

  (it "blocks opponent from winning in diagonal case TL-BR"
    (should= 8 (best-move [pl ai 2
                            3 pl 5
                            ai 7 8] ai pl)))

  (it "blocks an opponent from winning in second column"
    (should= 7 (best-move [0 pl ai
                            ai pl 5
                            6 7 8] ai pl)))

  (it "wins in second column instead of blocking opponent in first column"
    (should= 1 (best-move [pl 1 2
                            3 ai pl
                            pl ai 8] ai pl)))

  (it "wins in third column over blocking in second column"
    (should= 5 (best-move [0 pl ai
                            3 4 5
                            pl pl ai] ai pl)))

  (it "wins in diagonal case TL-R"
    (should= 0 (best-move [0 pl 2
                            3 ai 5
                            pl pl ai] ai pl)))

  (it "wins in middle row over blocking in bottom row horizontal case"
    (should= 3 (best-move [0 pl 2
                            3 ai ai
                            pl pl 8] ai pl)))
)
