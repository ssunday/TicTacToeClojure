(ns -tictactoe.score_recording
  (:require [clojure.edn :as edn])
  (:use [clojure.java.io]))

(def player-tally-file-name "tally.edn")
