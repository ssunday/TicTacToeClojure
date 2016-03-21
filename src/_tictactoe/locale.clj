(ns -tictactoe.locale)

(def default-loc (keyword "en"))

(defn loc []
  (or (keyword (System/getenv "LOC"))
      default-loc))
