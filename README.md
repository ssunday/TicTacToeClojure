# Tic Tac Toe

A Clojure application to play Tic Tac Toe.

Features:

* Human v Human, AI v. AI, or Human v. AI. (AI currently only works with 3x3)
* Can choose what marker to use.
* Can set first player
* Board can either be 3x3 or 4x4
* Board output is colored.
* Localized to both English and German.
* Scores persist to a local file. Either JSON or EDN. Default is JSON.

## Usage

Clone to desktop and enter /TicTacToeClojure directory and run:

```
lein run
```

Type in LOC=de before lein run for German localization. Default is English.
For EDN file persistence, type in TYPE=edn. JSON is default or TYPE=json.

##Testing

To run the tests, you need to have Speclj and use the lein spec command.
