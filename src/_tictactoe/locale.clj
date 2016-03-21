(ns -tictactoe.locale)

(defn loc []
  (or (keyword (System/getenv "LOC"))
      :en))
