;Responsible for using Tower to create a dictionary for the English and other language phrases used for input and output.

(ns -tictactoe.ttt.localization
  (:require [taoensso.tower :as tower :refer (with-tscope)]))

(def t-config
  {:dictionary {:en {:input {
                              :spot-to-be-marked "Please input spot to be marked. Must be 0 - %s and open."
                              :player-one-name "Please input player one's name."
                              :player-two-name "Please input player two's name."
                              :player-one-marker "Please input player one's marker. Marker must be a single character long and not a number."
                              :player-two-marker "Please input player two's marker. Marker must be a single character long, not a number, and different from player one's marker."
                              :player-one-ai "Input either y/n for player one being an AI."
                              :player-two-ai "Input either y/n for player two being an AI."
                              :player-going-first "Please input marker of player going first. Either: %1$s or %2$s."
                              :board-dimension "Choose either 3x3 or 4x4 board. Type 3 for 3x3 and 4 for 4x4."
                              :play-again-same-input "Play again with same input? (y/n)"
                              :play-again-new-input "Play again with new input? (y/n)"
                              :yes-option "y"
                              :no-option "n"
                            }
                    :output {
                              :welcome-message "Welcome to the Tic Tac Toe Game!"
                              :current-player-marker "Current Player Marker: %s"
                              :player-names "These are the players with scores tallied from previous games. If you are one of these players, please choose that same name. If you are not, you will modify their scores by choosing that name."
                              :player-tally "Player Tally"
                              :tally-header "W/L/D"
                              :player-one-has-won "Player One has Won!"
                              :player-two-has-won "Player Two has Won!"
                              :game-has-been-tied "Tied!"
                              :end-game-message "Thank you for playing!"
                            }
                    :error-messages {
                                    :spot-not-open "Spot must be open and unmarked."
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
                    :menu {
                          :menu "Menu"
                          :select-menu-option "Please select option by typing in its number."
                          :play-game "Play Game"
                          :see-scores "See Scores"
                          :end-application "End Application"
                          }
                    :web {
                         :game-settings "Game Settings"
                         :game-scores "Game Scores"
                         :click-here "Click here"
                         :page-not-found "Page not found"
                         :sorry-page-not-found "Sorry, but that page was not found!"
                         :game-over "Game Over"
                         :set-player-names "Please set unique names for each player. This will be used for score recording."
                         :select-unique-markers "Please select unique markers for each player. "
                         :set-player-one-marker "Choose player one marker:"
                         :set-player-two-marker "Choose player two marker:"
                         :error-markers-or-names-are-same "Player markers and names must be different."
                         :choose-board-dimension "Board Dimension"
                         :ai-works-for-3x3 "AI only works for a 3x3 board."
                         :player-one-will-be-human "Player One will be a Human Player:"
                         :player-one-will-be-ai "Player One will be an AI player:"
                         :player-two-will-be-human "Player Two will be a Human Player:"
                         :player-two-will-be-ai "Player Two will be an AI player:"
                         :choose-player-that-goes-first "Choose player that will go first:"
                         :player-one "Player One:"
                         :player-two "Player Two:"
                         :next "Next"
                         :play-again "Play Again"
                         :return-home "To return to home"
                         }
                    :missing "Translation missing"}
                :de {:input {
                             :spot-to-be-marked "Bitte geben Sie Ort und Stelle zu kennzeichnen. Muss 0 bis %s und offen sein."
                             :player-one-name "Bitte geben erste Spielernamen."
                             :player-two-name "Bitte geben Sie Name des zweiten Spielers."
                             :player-one-marker "Bitte geben Sie Spieler einen der Marker. Marker muss ein einzelnes Zeichen lang und nicht eine Zahl sein."
                             :player-two-marker "Bitte geben Spieler zwei der Marker. Marker muss ein einzelnes Zeichen lang sein, keine Zahl, und unterscheidet sich von Spieler einen der Markierung."
                             :player-one-ai "Input entweder j/n für den Spieler eine eine künstliche Intelligenz zu sein."
                             :player-two-ai "Input entweder j/n für zwei Spieler eine künstliche Intelligenz zu sein."
                             :player-going-first "Bitte geben Sie Marker der Spieler geht zuerst Entweder: %1$s oder %2$s"
                             :board-dimension "Wählen Sie entweder 3x3 oder 4x4 Bord. Typ 3 für 3x3 und 4 für 4x4."
                             :play-again-same-input "Play wieder mit dem gleichen Eingang? (j/n)"
                             :play-again-new-input "Play wieder mit neuen Eingang? (j/n)"
                             :yes-option "j"
                             :no-option "n"
                           }
                    :output {
                             :welcome-message "Willkommen in der Tic Tac Toe-Spiel!"
                             :current-player-marker "Aktuelle Spieler Marker: %s"
                             :player-names "Dies sind die Spieler mit aus früheren Spielen ausgezählt Partituren. Wenn Sie einen dieser Spieler sind, wählen Sie bitte, dass die gleichen Namen. Wenn Sie nicht sind, werden Sie ihre Werte ändern, indem Sie den Namen auswählen."
                             :player-tally "Spieler Tally:"
                             :tally-header "S/V/R"
                             :player-one-has-won "Erste Spieler hat Gewonnen!"
                             :player-two-has-won "Zweite Spieler hat Gewonnen!"
                             :game-has-been-tied "Spiel wurde Gebunden!"
                             :end-game-message "Danke für das Spiel"}
                    :error-messages {
                                     :spot-not-open "Stelle muss offen sein und nicht markierte."
                                     :spot-is-invalid-input "Punkt muss eine ganze Zahl entsprechend einer offenen Position sein."
                                     :spot-is-not-on-board "Punkt muss eine Position auf dem Spielbrett sein."
                                     :name-already-taken "Dieser Name ist bereits getan."
                                     :name-must-not-be-blank "Der Name darf nicht leer sein."
                                     :bad-player-one-marker "Marker muss ein einzelnes Zeichen und keine Zahl sein."
                                     :bad-player-two-marker "Marker muss eindeutig, einzelne Länge sein, und keine Zahl."
                                     :invalid-player-going-first "Eingang eines der bereits definierten Marker Bitte Entweder: %1$s oder %2$s."
                                     :invalid-board-dimension "Typ 3 für 3x3 und 4 für 4x4."
                                     :invalid-menu-option "Option muss eine Zahl mit einer der verfügbaren Optionen angepasst werden."
                                     :bad-y-or-n-option "Bitte geben Sie entweder j oder n."
                                    }
                    :menu {
                           :menu "Menü"
                           :select-menu-option "Bitte wählen Sie, indem Sie im Eingabe seiner Nummer."
                           :play-game "Spielen"
                           :see-scores "Siehe Ergebnisse"
                           :end-application "End-Anwendung"
                          }
                    :web {
                         :game-settings "Spieleinstellungen"
                         :game-scores "Spielergebnisse"
                         :click-here "Klick hier"
                         :page-not-found "Seite nicht gefunden"
                         :sorry-page-not-found "Sorry, aber diese Seite wurde nicht gefunden!"
                         :game-over "Spiel ist aus"
                         :set-player-names "Bitte setzen Sie einen eindeutigen Namen für jeden Spieler. Dies wird für die Partitur Aufzeichnung verwendet werden."
                         :select-unique-markers "Bitte wählen Sie eindeutige Marker für jeden Spieler."
                         :set-player-one-marker "Spieler auswählen eine Marker:"
                         :set-player-two-marker "Spieler auswählen zwei Marker:"
                         :error-markers-or-names-are-same "Spieler marker und Namen müssen unterschiedlich sein."
                         :choose-board-dimension "Brett-Dimension"
                         :ai-works-for-3x3 "Künstliche Intelligenz-Player funktioniert nur für eine 3x3-Brett."
                         :player-one-will-be-human "Player eine wird ein menschlicher Spieler sein:"
                         :player-one-will-be-ai "Spieler eine wird künstliche Intelligenz Spieler sein:"
                         :player-two-will-be-human "Spieler zwei wird ein menschlicher Spieler sein:"
                         :player-two-will-be-ai "Spieler zwei wird künstliche Intelligenz Spieler sein:"
                         :choose-player-that-goes-first "Wählen Sie Spieler die zuerst gehen:"
                         :player-one "Spieler Ein:"
                         :player-two "Spieler Zwei:"
                         :next "Nächster"
                         :play-again "Spiel nochmal"
                         :return-home "Zur Rückkehr zur Startseite"
                         }
                    :missing "Übersetzung fehlt"}}
                :fallback-locale :en})

(def translate (tower/make-t t-config))