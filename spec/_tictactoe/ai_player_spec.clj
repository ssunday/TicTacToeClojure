(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(def ai "X")

(def pl "O")

(describe "get-available-locations"
  (it "returns only numbered, open spots for partially marked 3x3 board"
    (should= [0 3 4 5] (get-available-spots [0 ai pl 3 4 5 ai ai pl])))

  (it "returns entire 3x3 board when nothing has been marked"
    (should= [0 1 2 3 4 5 6 7 8] (get-available-spots [0 1 2 3 4 5 6 7 8])))

  (it "returns entire 4x4 board when nothing has been marked"
    (should= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] (get-available-spots [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])))

  (it "returns only numbered, open spots for partially marked 4x4 board"
    (should= [0 3 4 5 7 8 10 11 15] (get-available-spots [0 ai pl 3
                                                   4 5 pl 7
                                                   8 ai 10 11
                                                  ai pl pl 15]))))

(describe "score"
  (it "returns 0 for a tied board of depth 0"
    (should= 0 (get-score [pl ai ai
                       ai pl pl
                       pl ai ai] pl ai 0)))

  (it "returns 0 for a tied board of depth 10"
    (should= 0 (get-score [pl ai ai
                       ai pl pl
                       pl ai ai] pl ai 10)))

  (it "returns 100 for a won board by ai previous turn of depth 0"
    (should= 100 (get-score [ai ai ai
                       ai pl pl
                       pl ai pl] pl ai 0)))
  (it "returns 90 for a won board by ai of depth 0"
    (should= 90 (get-score [ai ai ai
                       ai pl pl
                       pl ai pl] pl ai 10)))
  (it "returns -100 for a won board by other player of depth 0"
    (should= -100 (get-score [ai ai ai
                       ai pl pl
                       pl ai pl] ai ai 0)))
  (it "returns -95 for a won board by other player of depth 5"
    (should= -95 (get-score [ai ai ai
                       ai pl pl
                       pl ai pl] ai ai 5))))

(describe "best-move"

  (context "on 3x3 board"

    (it "returns top left corner spot on empty board"
      (should= 0 (best-move [0 1 2 3 4 5 6 7 8] ai pl)))

    (it "returns top left corner spot when center is marked"
      (should= 0 (best-move [0 1 2 3 pl 5 6 7 8] ai pl)))

    ; (it "returns middle spot when top left is marked"
    ;   (should= 4 (best-move [pl 1 2
    ;                           3 4 5
    ;                           6 7 8] ai pl)))

    ; (it "returns middle spot when it has marked top left and the player has marked another location"
    ;   (should= 4 (best-move [ai pl 2
    ;                           3 4 5
    ;                           6 7 8] ai pl)))

    (it "takes the top middle selection when top left and middle are taken"
      (should= 1 (best-move [ai 1 2
                              3 pl 5
                              6 7 8] ai pl)))
    (it "takes right middle spot when opponent has 2 in a row"
      (should= 5 (best-move [ai ai pl
                              pl pl 5
                              ai 7 8] ai pl)))

    (it "returns winning spot on board where it has two in a row"
      (should= 2 (best-move [ai ai 2 3 4 5 6 7 8] ai pl)))

    (it "returns winning spot on board where it has two in a row"
      (should= 6 (best-move [ai pl pl ai 4 5 6 7 8] ai pl)))

    (it "blocks winning spot on board where opponent has two in a row"
      (should= 2 (best-move [pl pl 2
                              3 4 5
                              6 7 8] ai pl)))

    (it "blocks winning spot on board where opponent has two in the third row"
      (should= 6 (best-move [ai 1 2
                              3 4 5
                              6 pl pl] ai pl)))

    (it "blocks winning spot on board where opponent can win with a diagonal"
      (should= 2 (best-move [ai 1 2
                              ai pl 5
                              pl 7 8] ai pl)))

    (it "blocks winning spot on board where opponent has two in a row"
      (should= 8 (best-move [pl ai 2
                              3 pl ai
                              6 7 8] ai pl)))

    (it "takes a win instead of block last column."
      (should= 5 (best-move [ai pl ai
                             ai pl 5
                             pl 7 ai] ai pl)))

    (it "wins when it has the opportunity"
      (should= 8 (best-move [ai pl pl
                              3 ai 5
                              6 7 8] ai pl)))

    (it "blocks opponent from winning in diagonal case L-R"
      (should= 8 (best-move [pl ai 2
                              3 pl 5
                              ai  7  8] ai pl)))

    (it "blocks opponent from winning in diagonal case R-L"
      (should= 6 (best-move [ai ai pl
                              3 pl 5
                              6  7  8] ai pl)))

    (it "blocks an opponent from winning second column"
      (should= 7 (best-move [pl pl ai
                              ai pl 5
                              6 7 8] ai pl)))

    (it "blocks an opponent from winning on top row horizontal"
      (should= 2 (best-move [pl pl 2
                              ai 4 5
                              6 7 8] ai pl)))

    (it "wins instead of blocking opponent by selecting spot 1"
      (should= 1 (best-move [pl 1 2
                              3 ai pl
                              pl ai 8] ai pl)))

    (it "blocks opponent from winning in second column by selecting four"
      (should= 5 (best-move [0 pl ai
                              3 4 5
                              pl pl ai] ai pl)))

    (it "wins in diagonal case TL-R"
      (should= 0 (best-move [0 pl ai
                              3 ai 5
                              pl pl ai] ai pl)))

    (it "wins when it could also block in mid row horizontal"
      (should= 3 (best-move [0 pl 2
                              3 ai ai
                              pl pl 8] ai pl)))

    (it "wins in diagonal case L-R"
      (should= 8 (best-move [ai pl 2
                              3 ai 5
                              pl pl 8] ai pl)))

    (it "wins in diagonal case R-L"
      (should= 6 (best-move [0 pl ai
                              pl ai 5
                              6 pl 8] ai pl))))
)
