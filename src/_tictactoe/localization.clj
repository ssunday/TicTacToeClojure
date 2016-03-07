(ns -tictactoe.localization
  (:require [taoensso.tower :as tower :refer (with-tscope)]))

(def t-config
  {:dictionary {:en {:output {:welcome-message "Welcome to the Tic Tac Toe Game!"
                              :current-player-marker "Current Player Marker: %s"
                              :end-game-message "Thank you for playing!"}
                    :missing "Translation missing"}
                :de {:output {:welcome-message "Willkommen in der Tic Tac Toe-Spiel!"
                              :current-player-marker "Aktuelle Spieler Marker: %s"
                              :end-game-message "Danke für das Spiel"}
                    :missing "Übersetzung fehlt"}}
                :fallback-locale :en})

(def translate (tower/make-t t-config))
