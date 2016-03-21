;Calls the function that runs the application.

(ns -tictactoe.core
  (:require [-tictactoe.scoring_edn :refer :all]
            [-tictactoe.scoring_json :refer :all]
            [-tictactoe.scoring_postgres :refer :all]
            [-tictactoe.play_game_option :refer :all]
            [-tictactoe.display_previous_scores_option :refer :all]
            [-tictactoe.end_application_option :refer :all]
            [-tictactoe.game_runner :as runner]))

(defn -main []
  (runner/run))
