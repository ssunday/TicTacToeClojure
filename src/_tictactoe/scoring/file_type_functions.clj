;Responsible for methods that all file types can use.

(ns -tictactoe.scoring.file_type_functions
  (:use [clojure.java.io]))

(defn- file-exists [file-name]
  (-> file-name
      file
      .exists))

(defn clear-file [file-name]
  (if (file-exists file-name)
    (delete-file file-name)))

(defn write-to-file [file-name data-to-write]
  (spit file-name data-to-write :append true))

(defn get-file-lines [file-name]
  (if (file-exists file-name)
    (with-open [rdr (reader file-name)]
        (doall (line-seq rdr)))))
