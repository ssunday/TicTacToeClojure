;Responsible for the schema of how the player wins/losses/tallies are created.

(ns -tictactoe.ttt.scoring_schema)

(def wins (keyword "wins"))

(def losses (keyword "losses"))

(def draws (keyword "draws"))

(def wins-losses-draws [wins losses draws])

(def default-wins-losses-draws-scores (zipmap wins-losses-draws (repeat 0)))
