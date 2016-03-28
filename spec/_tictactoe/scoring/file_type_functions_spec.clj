(ns -tictactoe.scoring.file_type_functions-spec
  (:require [speclj.core :refer :all]
            [-tictactoe.scoring.file_type_functions :refer :all])
  (:use [clojure.java.io]))

(describe "clear-file"

  (it "deletes file if it exists"
    (let [file-name "it-exists.txt"]
      (spit file-name 1)
      (clear-file file-name)
      (should-not (.exists (file file-name)))))

  (it "files still does not exist if it did not exist"
    (let [file-name "it-does-not-exist.txt"]
      (clear-file file-name)
      (should-not (.exists (file file-name))))))

(describe "write-to-file"

  (it "appends given data to given file"
    (let [file-name "write-to-file.txt"
          data1 "1"
          data2 "2"]
      (write-to-file file-name data1)
      (write-to-file file-name data2)
      (let [lines (slurp file-name)]
        (delete-file file-name)
        (should= (str data1 data2) lines)))))

(describe "get-file-lines"

  (it "returns a list of all the lines in the file"
    (let [file-name "get-file-lines.txt"
          data1 "1"
          data2 "2"]
      (spit file-name (str data1 "\n"))
      (spit file-name data2 :append true)
      (let [lines (get-file-lines file-name)]
        (delete-file file-name)
        (should= (list data1 data2) lines)))))
