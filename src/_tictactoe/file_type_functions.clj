; Responsible for methods that all file types can use.

(ns -tictactoe.file_type_functions
  (:use [clojure.java.io]))

(defn file-exists [file-name]
  (-> file-name
      file
      .exists))
      
(defn clear-file [file-name]
  (if (file-exists file-name)
    (delete-file file-name)))
