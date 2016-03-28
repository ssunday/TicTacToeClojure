(ns -tictactoe.web.input_validation-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.web.input_validation :refer :all]))

(describe "markers-and-names-are-not-the-same"

  (it "returns true when both the markers and names are unique"
    (let [marker_one "X"
          marker_two "O"
          name_one "Bob"
          name_two "Jill"]
      (should (markers-and-names-are-not-the-same marker_one marker_two name_one name_two))))

  (it "returns false when names are the same"
    (let [marker_one "X"
          marker_two "O"
          name_one "Bob"
          name_two name_one]
      (should-not (markers-and-names-are-not-the-same marker_one marker_two name_one name_two))))

  (it "returns false when markers are the same"
    (let [marker_one "X"
          marker_two marker_one
          name_one "Bob"
          name_two "Jill"]
      (should-not (markers-and-names-are-not-the-same marker_one marker_two name_one name_two))))

  (it "returns false when both are same"
    (let [marker_one "X"
          marker_two marker_one
          name_one "Bob"
          name_two name_one]
      (should-not (markers-and-names-are-not-the-same marker_one marker_two name_one name_two)))))

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
