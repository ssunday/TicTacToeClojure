(ns -tictactoe.web.play_game_presenter)

(defn- get-form-start []
  "<table><form action='/play_game' method='post'>")

(defn- begin-row [index dimension]
  (if (= (mod index dimension) 0)
      "<tr>"
      ""))

(defn- begin-cell []
  "<td style='padding:0 15px 0 15px;'>")

(defn- cell-value [board index]
  (if (number? (get board index))
      (format "<input type='radio' name='spot' value=%s checked>" (get board index))
      (format "%s" (get board index))))

(defn- end-cell []
  "</td>")

(defn- end-row [index dimension]
  (if (= (mod index dimension) (dec dimension))
      "</tr>"
      ""))

(defn- end-board []
  "</table>
    <br>
    <button type='submit'>Next Turn</button>
  </form>")

(defn display-board [board]
  (let [string-board ""
        dimension (int (Math/sqrt (count board)))]
    (for [index (range (count board))]
      (-> string-board
        (str (begin-row index dimension))
        (str (begin-cell))
        (str (cell-value board index))
        (str (end-cell))
        (str (end-row index dimension))))))
