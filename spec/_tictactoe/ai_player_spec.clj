(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(def ai-marker "X")

(def other-marker "O")

(describe "get-available-locations"
  (it "returns only numbered, open spots for partially marked 3x3 board"
    (should= [0 3 4 5] (get-available-spots [0 ai-marker other-marker 3 4 5 ai-marker ai-marker other-marker])))
  (it "returns entire 3x3 board when nothing has been marked"
    (should= [0 1 2 3 4 5 6 7 8] (get-available-spots [0 1 2 3 4 5 6 7 8])))
  (it "returns entire 4x4 board when nothing has been marked"
    (should= [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15] (get-available-spots [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])))
  (it "returns only numbered, open spots for partially marked 4x4 board"
    (should= [0 3 4 5 7 8 10 11 15] (get-available-spots [0 ai-marker other-marker 3 
                                                   4 5 other-marker 7
                                                   8 ai-marker 10 11
                                                  ai-marker other-marker other-marker 15])))
)

(describe "best-move"

  (context "3x3 board"

    (it "returns corner spot on empty board"
      (should= 0 (best-move [0 1 2 3 4 5 6 7 8] ai-marker other-marker)))

    (it "returns top left corner spot when center is marked board"
      (should= 0 (best-move [0 1 2 3 other-marker 5 6 7 8] ai-marker other-marker)))

    (it "returns winning spot on board where it has two in a row"
      (should= 2 (best-move [ai-marker ai-marker 2 3 4 5 6 7 8] ai-marker other-marker)))

    (it "returns winning spot on board where it has two in a row"
      (should= 6 (best-move [ai-marker other-marker other-marker ai-marker 4 5 6 7 8] ai-marker other-marker)))

    (it "blocks winning spot on board where opponent has two in a row"
      (should= 2 (best-move [other-marker other-marker 2 3 4 5 6 7 8] ai-marker other-marker)))

    (it "blocks winning spot on board where opponent has two in a row"
      (should= 8 (best-move [other-marker ai-marker 2
                              3 other-marker ai-marker
                              6 7 8] ai-marker other-marker)))

    (it "blocks opponent's winning spot even when it has 2 in a row."
      (should= 7 (best-move [ai-marker other-marker ai-marker
                             ai-marker other-marker 5
                             other-marker 7 ai-marker] ai-marker other-marker)))

    (it "wins when it has the opportunity"
      (should= 8 (best-move [ai-marker other-marker other-marker
                              3 ai-marker 5
                              6 7 8] ai-marker other-marker)))

    (it "blocks opponent from winning in diagonal case L-R"
      (should= 8 (best-move [other-marker ai-marker 2
                              3 other-marker 5
                              ai-marker  7  8] ai-marker other-marker)))

    (it "blocks opponent from winning in diagonal case R-L"
      (should= 6 (best-move [ai-marker ai-marker other-marker
                              3 other-marker 5
                              6  7  8] ai-marker other-marker)))

    (it "blocks an opponent from winning second column"
      (should= 7 (best-move [other-marker other-marker ai-marker
                              ai-marker other-marker 5
                              6 7 8] ai-marker other-marker)))

    (it "blocks an opponent from winning on top row horizontal"
      (should= 2 (best-move [other-marker other-marker 2
                              ai-marker 4 5
                              ai-marker 7 other-marker] ai-marker other-marker))))
  ; (context "4x4 board"
  ;   (it "returns winning spot on board where it has three in a row"
  ;     (should= 2 (best-move [ai-marker ai-marker 2 ai-marker
  ;                             4 other-marker 6 7
  ;                             8 9 10 11
  ;                             other-marker 13 14 15] ai-marker other-marker))))

)
