;Responsible for the details of running the game.

(ns -tictactoe.console.play_game_functions
  (:require [-tictactoe.console.player_input :as player]
            [-tictactoe.ttt.ai_player :as ai]
            [-tictactoe.ttt.scoring_schema :as schema]))

(defn update-player-tally-win [player-tally player-one-won]
  (let [player-one-name (-> @player-tally first first)
        player-two-name (-> @player-tally second first)
        winning-player-name (if player-one-won player-one-name player-two-name)
        losing-player-name (if player-one-won player-two-name player-one-name)]
    (swap! player-tally update-in [winning-player-name schema/wins] inc)
    (swap! player-tally update-in [losing-player-name schema/losses] inc)))

(defn update-player-tally-draw [player-tally]
  (dorun (map #(swap! player-tally update-in [(first %) schema/draws] inc) @player-tally)))

(defn winning-player-is-player-one [current-player-marker player-one-marker]
  (not= current-player-marker player-one-marker))

(defn get-spot-to-be-marked [board current-player-marker other-player-marker current-player-is-ai]
    (if current-player-is-ai
      (ai/best-move board current-player-marker other-player-marker)
      (player/get-player-spot-to-be-marked board)))

(defn get-other-player-marker [first-player player-one-marker player-two-marker]
  (if (= first-player player-one-marker)
      player-two-marker
      player-one-marker))

(defn initial-player-tally [player-one-name player-two-name]
  (-> [player-one-name player-two-name]
      (zipmap (repeat schema/default-wins-losses-draws-scores))
      atom))
