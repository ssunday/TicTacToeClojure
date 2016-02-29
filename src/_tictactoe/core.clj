(ns -tictactoe.core
  (:require [-tictactoe.game_functions :as gf])
  (:require [-tictactoe.console_io :as io]))

(defn -main []
  (io/start-game-message)
  (let [player-one-marker (io/get-player-one-marker)
        player-two-marker (io/get-player-two-marker player-one-marker)]
        (println "Player one marker: " player-one-marker)
        (println "Player two marker: " player-two-marker))
  
)
