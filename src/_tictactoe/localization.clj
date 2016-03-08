(ns -tictactoe.localization
  (:require [taoensso.tower :as tower :refer (with-tscope)]))

(def t-config
  {:dictionary {:en {:input {:spot-to-be-marked "Please input spot to be marked. Must be 0 - %s and open."
                             :player-one-name "Please input player one's name."
                             :player-two-name "Please input player two's name."
                            :player-one-marker "Please input player one's marker. Marker must be a single character long and not a number."
                            :player-two-marker "Please input player two's marker. Marker must be a single character long, not a number, and different from player one's marker."
                            :player-one-ai "Input either y/n for player one being an AI."
                            :player-two-ai "Input either y/n for player two being an AI."
                            :player-going-first "Please input marker of player going first. Either: %1$s or %2$s."
                            :board-dimension "Choose either 3x3 or 4x4 board. Type 3 for 3x3 and 4 for 4x4."
                            :play-again-same-input "Play again with same input? (y/n)"
                            :play-again-new-input "Play again with new input? (y/n)"}
                    :output {:welcome-message "Welcome to the Tic Tac Toe Game!"
                              :current-player-marker "Current Player Marker: %s"
                              :player-names "These are the players with scores tallied from previous games. If you are one of these players, please choose that same name or else you will modify their scores."
                              :player-tally "Player Tally"
                              :tally-header "W/L/D"
                              :player-one-has-won "Player One has Won!"
                              :player-two-has-won "Player Two has Won!"
                              :game-has-been-tied "Tied!"
                              :end-game-message "Thank you for playing!"}
                    :error-messages {:spot-not-open "Spot must be open and unmarked."
                                    :spot-is-invalid-input "Spot must be an integer corresponding to an open location."
                                    :spot-is-not-on-board "Spot must be a location on the game board."
                                    :name-already-taken "That name has already been taken."
                                    :name-must-not-be-blank "Your name cannot be blank."
                                    :bad-player-one-marker "Marker must be a single character and not a number."
                                    :bad-player-two-marker "Marker must be unique, single length, and not a number."
                                    :invalid-player-going-first "Please input one of the already defined markers. Either: %1$s or %2$s"
                                    :invalid-board-dimension "Type 3 for 3x3 and 4 for 4x4."
                                    :invalid-menu-option "Option must be a number matched with one of the available options."
                                    :bad-y-or-n-option "Please input either y or n."
                                    }
                    :menu {:menu "Menu"
                          :select-menu-option "Please select option by typing in its number."
                          :play-game "Play Game"
                          :see-scores "See Scores"
                          :end-application "End Application"}
                    :missing "Translation missing"}
                :de {:input {:spot-to-be-marked "Bitte geben Sie Ort und Stelle zu kennzeichnen. Muss 0 bis %s und offen sein."
                             :player-one-name "Bitte geben erste Spielernamen."
                             :player-two-name "Bitte geben Sie Name des zweiten Spielers."
                             :player-one-marker "Bitte geben Sie Spieler einen der Marker. Marker muss ein einzelnes Zeichen lang und nicht eine Zahl sein."
                             :player-two-marker "Bitte geben Spieler zwei der Marker. Marker muss ein einzelnes Zeichen lang sein, keine Zahl, und unterscheidet sich von Spieler einen der Markierung."
                             :player-one-ai "Input entweder y/n für den Spieler eine eine künstliche Intelligenz zu sein."
                             :player-two-ai "Input entweder y / n für zwei Spieler eine künstliche Intelligenz zu sein."
                             :player-going-first "Bitte geben Sie Marker der Spieler geht zuerst Entweder: %1$s oder %2$s"
                             :board-dimension "Wählen Sie entweder 3x3 oder 4x4 Bord. Typ 3 für 3x3 und 4 für 4x4."
                             :play-again-same-input "Play wieder mit dem gleichen Eingang? (y/n)"
                             :play-again-new-input "Play wieder mit neuen Eingang? (y/n)"}
                    :output {:welcome-message "Willkommen in der Tic Tac Toe-Spiel!"
                              :current-player-marker "Aktuelle Spieler Marker: %s"
                              :player-names "Dies sind die Spieler mit aus früheren Spielen ausgezählt Partituren. Wenn Sie einen dieser Spieler sind, wählen Sie bitte, dass die gleichen Namen oder sonst werden Sie ihre Noten zu ändern."
                              :player-tally "Spieler Tally:"
                              :tally-header "W/L/D"
                              :player-one-has-won "Erste Spieler hat Gewonnen!"
                              :player-two-has-won "Zweite Spieler hat Gewonnen!"
                              :game-has-been-tied "Spiel wurde Gebunden!"
                              :end-game-message "Danke für das Spiel"}
                    :error-messages {:spot-not-open "Stelle muss offen sein und nicht markierte."
                                    :spot-is-invalid-input "Punkt muss eine ganze Zahl entsprechend einer offenen Position sein."
                                    :spot-is-not-on-board "Punkt muss eine Position auf dem Spielbrett sein."
                                    :name-already-taken "Dieser Name ist bereits getan."
                                    :name-must-not-be-blank "Der Name darf nicht leer sein."
                                    :bad-player-one-marker "Marker muss ein einzelnes Zeichen und keine Zahl sein."
                                    :bad-player-two-marker "Marker muss eindeutig, einzelne Länge sein, und keine Zahl."
                                    :invalid-player-going-first "Eingang eines der bereits definierten Marker Bitte Entweder: %1$s oder %2$s."
                                    :invalid-board-dimension "Typ 3 für 3x3 und 4 für 4x4."
                                    :invalid-menu-option "Option muss eine Zahl mit einer der verfügbaren Optionen angepasst werden."
                                    :bad-y-or-n-option "Bitte geben Sie entweder y oder n."
                                    }
                    :menu {:menu "Menü"
                            :select-menu-option "Bitte wählen Sie, indem Sie im Eingabe seiner Nummer."
                            :play-game "Spielen"
                            :see-scores "Siehe Ergebnisse"
                            :end-application "End-Anwendung"}
                    :missing "Übersetzung fehlt"}}
                :fallback-locale :en})

(def translate (tower/make-t t-config))
