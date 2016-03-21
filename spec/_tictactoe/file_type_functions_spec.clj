(ns -tictactoe.file_type_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.file_type_functions :refer :all])
  (:use [clojure.java.io]))

(describe "file-exists"

  (it "returns true when file exists"
    (let [file "it-exists.txt"]
      (spit file 1)
      (should (file-exists file))
      (delete-file file)))

  (it "returns false when file does not exist"
    (let [file "it-does-not-exist.txt"]
      (should-not (file-exists file)))))

(describe "clear-file"
  (it "deletes file if it exists"
    (let [file "it-exists.txt"]
      (spit file 1)
      (delete-file file)
      (should-not (file-exists file))))

  (it "files still does not exist if it did not exist"
    (let [file "it-does-not-exist.txt"]
      (clear-file file)
      (should-not (file-exists file)))))
