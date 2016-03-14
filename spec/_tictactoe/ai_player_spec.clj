(ns -tictactoe.ai_player-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ai_player :refer :all]))

(def ai "X")

(def pl "O")

(def markers {:ai ai :opponent pl})

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

    (it "returns 0 for a tied board of depth 0"
      (should= 0 (get-score [pl ai ai
                         ai pl pl
                         pl ai ai] markers 0)))

    (it "returns 0 for a tied board of depth 5"
      (should= 0 (get-score [pl ai ai
                         ai pl pl
                         pl ai ai] markers 5)))

    (it "returns 10 for a won board by ai of depth 0"
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

(describe "get-next-player"

  (it "returns ai marker when opponent was current marker"
    (should= (:ai markers) (get-next-player markers (:opponent markers))))

  (it "returns opponent marker when ai was current marker"
    (should= (:opponent markers) (get-next-player markers (:ai markers)))))


(describe "apply-max-or-min"

  (it "applies max to the supplied mapping when the ai is the current player"
    (let [mapping [96 98 97 96 96 96]]
      (should= 98 (apply-max-or-min mapping markers (:ai markers)))))

  (it "applies min to the supplied mapping when the opponent is the current player"
    (let [mapping [-96 -98 -97 -96 -96 -96]]
      (should= -98 (apply-max-or-min mapping markers (:opponent markers))))))

(describe "get-best-move"

  (it "returns key of pair with largest value"
    (let [mapping {0 95 2 90 3 0 4 98}]
      (should= 4 (get-best-move mapping))))

  (it "returns key of pair with largest value"
    (let [mapping {0 98 2 90 3 0 4 97}]
      (should= 0 (get-best-move mapping))))

  (it "returns key of pair with largest value"
    (let [mapping {0 95 2 90 3 0 4 97 5 97 6 97}]
      (should= 6 (get-best-move mapping))))

  (it "returns key of pair with largest value"
    (let [mapping {0 -95 5 -97 7 -96}]
      (should= 0 (get-best-move mapping))))

  (it "returns highest key of pair with largest value"
    (let [mapping {0 96 2 90 3 90 4 96}]
      (should= 4 (get-best-move mapping)))))


(describe "best-move"

  (it "returns corner spot on empty board"
    (should= 8 (best-move [0 1 2
                           3 4 5
                           6 7 8] ai pl)))

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

  (it "returns bottom left corner if it has chosen bottom right and opponent is in top left"
    (should= 6 (best-move [pl 1 2
                           3 4 5
                           6 7 ai] ai pl)))

  (it "returns bottom middle if player occupies top right"
    (should= 7 (best-move [0 1 pl
                           3 4 5
                           6 7 ai] ai pl)))

  (it "returns spot 5 if bottom left is taken"
    (should= 5 (best-move [0 1 2
                           3 4 5
                           pl 7 ai] ai pl)))

  (it "takes middle spot if opponent takes 3 and 7"
    (should= 4 (best-move [0 1 2
                           pl 4 5
                           ai pl ai] ai pl)))

  (it "wins in TR-BL"
    (should= 2 (best-move [0 1 2
                           pl ai pl
                           ai pl ai] ai pl)))

  (it "takes winning move in diagonal case TL-BR"
    (should= 0 (best-move [0 1 pl
                           pl ai 5
                           ai pl ai] ai pl)))

  (it "takes winning move over blocking move when both middle spots are open in top and bottom rows"
    (should= 7 (best-move [pl 1 pl
                           3 4 5
                           ai 7 ai] ai pl)))

  (it "takes right middle spot when opponent has 2 in a row in that row"
    (should= 5 (best-move [ai ai pl
                           pl pl 5
                           ai 7 8] ai pl)))

  (it "blocks an opponent from winning in top row horizontal"
    (should= 2 (best-move [pl pl 2
                           3 ai 5
                           6 7 8] ai pl)))

  (it "blocks an opponent from winning in first row by selecting spot 3"
    (should= 3 (best-move [pl pl ai
                           3 ai 5
                           pl 7 8] ai pl)))

  (it "blocks an opponent from winning in the third column by selecting bottom right spot"
    (should= 8 (best-move [0 1 pl
                           3 ai pl
                           6 7 8] ai pl)))

  (it "blocks an opponent from winning in the top row"
    (should= 1 (best-move [pl 1 pl
                           3 ai pl
                           6 7 ai] ai pl)))

  (it "blocks opponent from winning in diagonal case TR-BL"
    (should= 6 (best-move [0 1 pl
                           3 pl 5
                           6 7 ai] ai pl)))

  (it "blocks opponent from winning in middle column by selecting spot 1"
    (should= 1 (best-move [0 1 pl
                           3 pl 5
                           ai pl ai] ai pl)))

  (it "blocks opponent from winning in middle column by selecting spot 7"
    (should= 7 (best-move [0 pl 2
                           ai pl pl
                           6 7 ai] ai pl)))

  (it "blocks opponent from winning in a TR-BL diagonal case"
    (should= 2 (best-move [0 pl 2
                           ai pl pl
                           pl ai ai] ai pl)))

  (it "returns winning spot on board where it has two in a row in the first column"
    (should= 6 (best-move [ai pl pl
                           ai 4 5
                           6 7 8] ai pl)))

  (it "wins in third column instead of blocking in middle column."
    (should= 5 (best-move [ai pl ai
                           ai pl 5
                           pl 7 ai] ai pl)))

  (it "wins when it has the opportunity in a diagonal TL-BR"
    (should= 8 (best-move [ai pl pl
                           3 ai 5
                           6 7 8] ai pl)))

  (it "wins in the first column"
    (should= 6 (best-move [ai 1 2
                           ai pl 5
                           6 7 pl] ai pl)))

  (it "wins in the second column"
    (should= 1 (best-move [0 1 pl
                           3 ai 5
                           pl ai 8] ai pl)))

  (it "wins when spot five is open and has marked 3 and 4"
    (should= 5 (best-move [pl pl ai
                           ai ai 5
                           pl pl 8] ai pl)))

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
                           pl pl 8] ai pl))))
