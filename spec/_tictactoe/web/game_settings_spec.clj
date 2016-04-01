(ns -tictactoe.web.game_settings-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.web.game_settings :refer :all]))

(describe "markers-map"
  (it "returns a hashmap of uppercase alphabet with key of :character and :selected of false for all except the one passed in"
    (should= (list {:character \A :selected false} {:character \B :selected false} {:character \C :selected false}
                   {:character \D :selected false} {:character \E :selected false} {:character \F :selected false}
                   {:character \G :selected false} {:character \H :selected false} {:character \I :selected false}
                   {:character \J :selected false} {:character \K :selected false} {:character \L :selected false}
                   {:character \M :selected false} {:character \N :selected false} {:character \O :selected false}
                   {:character \P :selected false} {:character \Q :selected false} {:character \R :selected false}
                   {:character \S :selected false} {:character \T :selected false} {:character \U :selected false}
                   {:character \V :selected false} {:character \W :selected false} {:character \X :selected true}
                   {:character \Y :selected false} {:character \Z :selected false})
             (markers-map \X))))

(describe "board-dimension-map"
  (it "returns a hashamp of board dimensions with keys/values saying whether they have been selected"
    (should= (list {:dimension "3" :selected true} {:dimension "4" :selected false}) (board-dimension-map "3"))))

(describe "first-player-map"
  (it "returns a hashmap of player marker keywords with whether it has been selected and the player title of one or two"
    (should= (list {:player "player-one-marker" :player-title "Player One" :selected true}
                   {:player "player-two-marker" :player-title "Player Two" :selected false})
             (first-player-map "player-one-marker" "Player One" "Player Two"))))

(describe "player-type-map"
  (it "returns a hashmap of player type and whether it is selected"
    (should= (list {:player-type "AI" :selected true}
                   {:player-type "Human" :selected false})
             (player-type-map "AI"))))
