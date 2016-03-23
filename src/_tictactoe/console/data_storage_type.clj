;Determines the type of data passed in by the command line as an environmental variable

(ns -tictactoe.console.data_storage_type)

(def default-type (keyword "json"))

(defn data-type []
  (or (keyword (System/getenv "TYPE"))
      default-type))
