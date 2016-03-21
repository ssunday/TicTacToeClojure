(ns -tictactoe.data_storage_type)

(def default-type (keyword "json"))

(defn data-type []
  (or (keyword (System/getenv "TYPE"))
      default-type))
