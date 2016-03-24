(ns -tictactoe.ttt.convert_string_to_number-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.ttt.convert_string_to_number :refer :all]))

(describe "convert-string-to-number"
  (it "returns nil for non number"
    (should= (convert-string-to-number "A") nil))
  (it "returns nil for blank space"
    (should= (convert-string-to-number "") nil))
  (it "returns number for string number"
    (should= (convert-string-to-number "1") 1)))
