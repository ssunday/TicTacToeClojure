# EN - Tic Tac Toe

A Clojure application to play Tic Tac Toe. Can either be played locally on a web browser (port 4000) or on the console.

Features:

* Human vs. Human, AI vs. AI, or Human vs. AI. (AI only works with 3x3)
* Can choose what marker to use.
* Can set which player goes first.
* Board can either be 3x3 or 4x4.
* Board output is colored.
* Localized to both English and German.
* Scores persist to a local file. Either JSON, EDN, or Postgres local database.
* Web or console version.
* Game can be replayed with same settings.

## Usage

Clone to desktop and enter /TicTacToeClojure directory and run either:

```
lein run console
```
or

```
lein run web
```

For the console and web version, respectively.

Type in LOC=de before lein run for German localization. Default is English.

For EDN file persistence, type in TYPE=edn. For saving to a locally run Postgres database, use TYPE=pg. JSON is default or TYPE=json.

Example of using Postgres and German localization on the console:

```
TYPE=pg LOC=de lein run console
```

For postgres persistence, connection configuration assumes //localhost:5432/tictactoe database url format. You must have a database created with the name tictactoe prior to running the game.

## Testing

To run the tests, you need to have Speclj and use the lein spec command. Before running, a database named 'test' must be created.

## Extending Tic Tac Toe

There are a few ways the Tic Tac Toe app can be extended without significantly modifying the source code. They are:

* Adding another data storage method. The instructions can be found in the 'Adding Another Storage Method' file.
* Adding another menu option. This can be done through adding another option file with the defmulti described in the game_menu file being implemented. The keyword used must be added to the game_menu hash of menu options and then the translations for it in English and German added to the localization file. Then require the file in the game_runner.
* Another language can be added by editing the localization file and adding translated phrases for every option present in the German and English maps.
* Another board size could be added by adding another winning-combinations method to the game_functions thing and adding the specified number to the input_validation valid dimensions vectors. However, one would also have to edited the respective localization lines corresponding with input and output on querying for board-dimensions. This requires more modification.
* Other methods of playing the game can be added by adjusting the core file and adding another condition.

# DE - Tic Tac Toe

Eine Anwendung Clojure Tic Tac Toe zu spielen. Kann entweder lokal auf einem Web-Browser oder auf der Konsole abgespielt werden.

Eigenschaften:

* Mensch gegen Mensch, AI gegen KI oder Mensch gegen KI. (AI funktioniert nur mit 3x3)
* Kann wählen, welche Marker zu verwenden.
* Kann eingestellt, welcher Spieler zuerst geht.
* Vorstand kann entweder 3x3 oder 4x4 sein.
* Board-Ausgang ist farbig.
* Lokalisierte auf Englisch und Deutsch.
* Ergebnisse bestehen in einer lokalen Datei. Entweder JSON, EDN oder Postgres lokalen Datenbank.
* Web oder Konsolen-Version.
* Spiel mit gleichen Einstellungen wiedergegeben werden.

## Verwendung

Clone auf den Desktop und geben Sie / TicTacToeClojure Verzeichnis und führen Sie entweder:

`` `
lein run console
`` `
oder

`` `
lein run web
`` `

Für die Konsole und Web-Version auf.

Geben Sie in LOC = de vor Lauf für deutsche Lokalisierung lein. Die Standardeinstellung ist Englisch.

Datei wird für EDN Persistenz, Typ in TYPE = edn. Zum Speichern auf eine lokal ausführen Postgres-Datenbank, verwenden Sie TYPE = pg. JSON ist Standard oder TYPE = json.

Beispiel für Postgres und deutsche Lokalisierung auf der Konsole:

```
TYPE=pg LOC=de lein run console
```

Für Postgres Persistenz, Verbindungskonfiguration übernimmt // localhost: 5432 / tictactoe Datenbank-URL-Format. Sie müssen eine Datenbank mit dem Namen tictactoe vor dem Ausführen des Spiels erstellt haben.

## Testen

Um die Tests auszuführen, müssen Sie Speclj und verwenden Sie die lein spec Befehl zu haben. Vor der Ausführung muss erstellt werden eine Datenbank 'test' genannt.

## Erweitern von Tic Tac Toe

Es gibt ein paar Möglichkeiten, die Tic Tac Toe App ohne wesentliche Modifikation des Quellcodes erweitert werden kann. Sie sind:

* Eine andere Datenspeichermethode Hinzufügen. Die Anweisungen können in der "Hinzufügen eines weiteren Speichermethode" Datei.
* Hinzufügen eines weiteren Menüoption. Dies kann durch das Hinzufügen eines weiteren Optionsdatei mit dem defmulti in der game_menu Datei wird implementiert beschrieben durchgeführt werden. Das Schlüsselwort verwendet wird, muss der game_menu Hash von Menüoptionen hinzugefügt werden und dann die Übersetzungen für sie in Englisch und Deutsch zur Lokalisierung Datei hinzugefügt. Dann benötigen Sie die Datei im game_runner.
* Sie können eine weitere Sprache durch Bearbeiten der Lokalisierungsdatei hinzugefügt werden, und Sätze übersetzt Hinzufügen für jede Option, die in der deutschen und englischen Karten.
* Könnte eine andere Plattengröße durch das Hinzufügen eines weiteren Gewinn-Kombinationen Verfahren zur game_functions Sache und das Hinzufügen der angegebenen Nummer an den input_validation gültigen Dimensionen Vektoren hinzugefügt werden. Allerdings würde man muss auch auf die Abfrage für Board-Abmessungen der jeweiligen Lokalisierungslinien entsprechend mit Eingangs- und Ausgangs bearbeitet. Dies erfordert mehr Modifikation.
* Andere Methoden des Spielens des Spiels kann durch Einstellung der Core-Datei und das Hinzufügen eines weiteren Bedingung hinzugefügt werden.
