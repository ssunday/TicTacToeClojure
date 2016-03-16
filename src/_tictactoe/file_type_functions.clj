(ns -tictactoe.file_type_functions
  (:use [clojure.java.io]))

(defn file-exists [file-name]
  (.exists (file file-name)))

(defn clear-file [file-name]
  (if (file-exists file-name)
    (delete-file file-name)))
