;Calls the function that runs the application. Also returns the particular object

(ns -tictactoe.core
  (:require [-tictactoe.console.game_runner :as console]
            [-tictactoe.web.handler :as web]))

(defn- select-option [option]
  (cond (= option "console") (console/run)
        (= option "web") (web/run)
        :else "ERROR: Not a valid option."))

(defn -main [arg]
  (select-option arg))
