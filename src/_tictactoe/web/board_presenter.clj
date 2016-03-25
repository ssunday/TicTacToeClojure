(ns -tictactoe.web.board_presenter)

(defn- begin-board []
  "<table><form action='/play_game' method='post'>")

(defn- begin-row [index dimension]
  (if (= (mod index dimension) 0)
      "<tr>"
      ""))

(defn- begin-cell []
  "<td style='padding:0 15px 0 15px;'>")

(defn- cell-value [board index current-player-is-ai]
  (if (and (number? (get board index))
           (not current-player-is-ai))
      (format "<input type='radio' name='spot' value=%s checked>" (get board index))
      (format "%s" (get board index))))

(defn- end-cell []
  "</td>")

(defn- end-row [index dimension]
  (if (= (mod index dimension) (dec dimension))
      "</tr>"
      ""))

(defn- end-board [button-label]
  (str "</table><br> <button type='submit'>" button-label "</button></form>"))

(defn- convert-to-string [board]
  (clojure.string/join "" (reverse board)))

(defn display-board [board current-player-is-ai button-label]
  (let [string-board (atom ())
        dimension (int (Math/sqrt (count board)))]
    (swap! string-board conj (begin-board))
    (doseq [index (range (count board))]
        (swap! string-board conj (begin-row index dimension))
        (swap! string-board conj (begin-cell))
        (swap! string-board conj (cell-value board index current-player-is-ai))
        (swap! string-board conj (end-cell))
        (swap! string-board conj (end-row index dimension)))
    (swap! string-board conj (end-board button-label))
    (convert-to-string @string-board)))
