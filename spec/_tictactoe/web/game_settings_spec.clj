(ns -tictactoe.web.game_settings-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.web.game_settings :refer :all]))

(describe "markers-map"
  (it "returns a hashmap of uppercase alphabet with key of :character and :selected of false for all except the letter, X, passed in"
    (should= (list {:character \A :selected false} {:character \B :selected false} {:character \C :selected false}
                   {:character \D :selected false} {:character \E :selected false} {:character \F :selected false}
                   {:character \G :selected false} {:character \H :selected false} {:character \I :selected false}
                   {:character \J :selected false} {:character \K :selected false} {:character \L :selected false}
                   {:character \M :selected false} {:character \N :selected false} {:character \O :selected false}
                   {:character \P :selected false} {:character \Q :selected false} {:character \R :selected false}
                   {:character \S :selected false} {:character \T :selected false} {:character \U :selected false}
                   {:character \V :selected false} {:character \W :selected false} {:character \X :selected true}
                   {:character \Y :selected false} {:character \Z :selected false})
             (markers-map "X")))
  (it "returns a hashmap of uppercase alphabet with key of :character and :selected of false for all except the letter, D, passed in"
    (should= (list {:character \A :selected false} {:character \B :selected false} {:character \C :selected false}
                   {:character \D :selected true} {:character \E :selected false} {:character \F :selected false}
                   {:character \G :selected false} {:character \H :selected false} {:character \I :selected false}
                   {:character \J :selected false} {:character \K :selected false} {:character \L :selected false}
                   {:character \M :selected false} {:character \N :selected false} {:character \O :selected false}
                   {:character \P :selected false} {:character \Q :selected false} {:character \R :selected false}
                   {:character \S :selected false} {:character \T :selected false} {:character \U :selected false}
                   {:character \V :selected false} {:character \W :selected false} {:character \X :selected false}
                   {:character \Y :selected false} {:character \Z :selected false})
             (markers-map "D")))
)

(describe "board-dimension-map"
  (it "returns a hashamp of board dimensions with board dimension 3 selected when it is passed in"
    (should= (list {:dimension "3" :selected true}
                   {:dimension "4" :selected false}) (board-dimension-map "3")))

  (it "returns a hashamp of board dimensions with board dimension 4 selected when it is passed in"
    (should= (list {:dimension "3" :selected false}
                   {:dimension "4" :selected true}) (board-dimension-map "4"))))

(describe "first-player-map"
  (it "returns a hashmap of player marker keywords with player one selected when it was chosen and the player title of one and two"
    (should= (list {:player "player-one-marker" :player-title "Player One" :selected true}
                   {:player "player-two-marker" :player-title "Player Two" :selected false})
             (first-player-map "player-one-marker" "Player One" "Player Two")))

  (it "returns a hashmap of player marker keywords with player two selected when it was chosen and the player title of one and two"
    (should= (list {:player "player-one-marker" :player-title "Player One" :selected false}
                   {:player "player-two-marker" :player-title "Player Two" :selected true})
             (first-player-map "player-two-marker" "Player One" "Player Two"))))

(describe "player-type-map"
  (it "returns a hashmap of player type with AI selected when it is selected"
    (should= (list {:player-type "AI" :player-type-display "AI" :selected true}
                   {:player-type "Human" :player-type-display "Human" :selected false})
             (player-type-map "AI" "AI" "Human")))

  (it "returns a hashmap of player type with Human selected when it is selected"
    (should= (list {:player-type "AI" :player-type-display "AI" :selected false}
                   {:player-type "Human" :player-type-display "Human" :selected true})
             (player-type-map "Human" "AI" "Human"))))
