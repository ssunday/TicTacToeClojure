;Calls the function that runs the application. Also returns the particular object

(ns -tictactoe.core
  (:require [-tictactoe.data_storage_factory :refer :all]
            [-tictactoe.play_game_option :refer :all]
            [-tictactoe.display_previous_scores_option :refer :all]
            [-tictactoe.end_application_option :refer :all]
            [-tictactoe.game_runner :as runner]))

(defn -main []
  (runner/run (data)))
