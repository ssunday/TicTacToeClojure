# Tic Tac Toe

A Clojure application to play Tic Tac Toe.

Features:

* Human v Human, AI v. AI, or Human v. AI. (AI currently only works with 3x3)
* Can choose what marker to use.
* Can set first player
* Board can either be 3x3 or 4x4
* Board output is colored.
* Localized to both English and German.
* Scores persist to a local file. Either JSON, EDN, or Postgres local database. Connection configuration assumes //localhost:5432/user-name format. See the scoring_postgres file for the schema and to change it.

## Usage

Clone to desktop and enter /TicTacToeClojure directory and run:

```
lein run
```

Type in LOC=de before lein run for German localization. Default is English.
For EDN file persistence, type in TYPE=edn. For saving to a locally run Postgres database, use TYPE=pg. JSON is default or TYPE=json.

## Testing

To run the tests, you need to have Speclj and use the lein spec command.

## Extending Tic Tac Toe

There are a few ways the Tic Tac Toe app can be extended without significantly modifying the source code. They are:

* Adding another data storage method. The instructions can be found in the 'Adding Another Storage Method' file.
* Adding another menu option. This can be done through adding another option file with the defmulti described in the game_menu file being implemented. The keyword used must be added to the game_menu hash of menu options and then the translations for it in English and German added to the localization file. Then require the file in the game_runner.
* Another language can be added by editing the localization file and adding translated phrases for every option present in the German and English maps.
* Another board size could be added by adding another winning-combinations method to the game_functions thing and adding the specified number to the input_validation valid dimensions vectors. However, one would also have to edited the respective localization lines corresponding with input and output on querying for board-dimensions. This requires more modification.
