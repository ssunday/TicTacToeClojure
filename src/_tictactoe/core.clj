;Calls the function that runs the application.

(ns -tictactoe.core
  (:use [-tictactoe.game_runner :only (run)]))

(defn -main []
  (run))
