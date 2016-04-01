(ns -tictactoe.web.handler-spec
  (:require [speclj.core :refer :all]
            [ring.mock.request :as mock]
            [-tictactoe.scoring.scoring_json :refer :all]
            [-tictactoe.ttt.scoring_repository :refer :all]
            [-tictactoe.web.handler :refer :all])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.locale :only (loc)]
        [ring-test.core :only (run-ring-app)]))

(describe "/"

  (it "fulfills response"
    (let [response (app (mock/request :get "/"))]
      (should= 200 (:status response))))

  (it "shows welcome message"
    (let [response (app (mock/request :get "/"))
          message (translate (loc) :output/welcome-message)]
      (should (clojure.string/includes? (:body response) message))))

  (it "shows play game option"
    (let [response (app (mock/request :get "/"))
          message (translate (loc) :menu/play-game)]
      (should (clojure.string/includes? (:body response) message))))

  (it "shows see scores option"
    (let [response (app (mock/request :get "/"))
          message (translate (loc) :menu/see-scores)]
      (should (clojure.string/includes? (:body response) message)))))

(describe "/settings"

  (context "GET"

    (it "fulfills response"
      (let [response (app (mock/request :get "/settings"))]
        (should= 200 (:status response))))

    (it "shows that it is game settings page"
      (let [response (app (mock/request :get "/settings"))
            message (translate (loc) :web/game-settings)]
        (should (clojure.string/includes? (:body response) message))))

    (it "does not show bad-marker-input error message by default"
      (let [response (app (mock/request :get "/settings"))
            message (translate (loc) :web/input-error)]
        (should-not (clojure.string/includes? (:body response) message))))

    (it "displays message that AI only works on 3x3 board"
      (let [response (app (mock/request :get "/settings"))
            message (translate (loc) :web/ai-works-for-3x3)]
        (should (clojure.string/includes? (:body response) message)))))

  (context "POST"

    (def valid-params {:player-one-marker "X"
                       :player-two-marker "O"
                       :player-one-name "Bob"
                       :player-two-name "Jane"
                       :player-one-type "AI"
                       :player-two-type "Human"
                       :first-player "player-one-marker"
                       :board-dimension "3"})

    (def invalid-params {:player-one-marker "X"
                         :player-two-marker "X"
                         :player-one-name "Bob"
                         :player-two-name "Jane"
                         :player-one-type "AI"
                         :player-two-type "Human"
                         :first-player "player-one-marker"
                         :board-dimension "3"})

    (it "fulfills response"
      (let [response (app (mock/request :post "/settings" valid-params))]
        (should= 200 (:status response))))

    (it "when settings are good enters the play game page"
      (let [response (run-ring-app app (mock/request :post "/settings" valid-params))
            message (translate (loc) :menu/play-game)]
        (should (clojure.string/includes? (:body response) message))))

    (it "does show bad-marker-input error message when markers are the name"
      (let [response (app (mock/request :post "/settings" invalid-params))
            message (translate (loc) :web/input-error)]
        (should (clojure.string/includes? (:body response) message))))))

(describe "/play_game"

  (context "basic game function"

    (def param1 {:player-one-marker "X"
                 :player-two-marker "O"
                 :player-one-name "Bob"
                 :player-two-name "Jane"
                 :player-one-type "Human"
                 :player-two-type "AI"
                 :first-player "player-one-marker"
                 :board-dimension "3"})

    (it "fulfills response"
      (let [response (run-ring-app app (mock/request :post "/settings" param1)
                                       (mock/request :post "/play_game" {:spot 0}))]
        (should= 200 (:status response))))

    (it "shows current marker message with other player's marker after initial turn"
      (let [response (run-ring-app app (mock/request :post "/settings" param1)
                                       (mock/request :post "/play_game" {:spot 0}))
            message (translate (loc) :output/current-player-marker (:player-two-marker param1))]
        (should (clojure.string/includes? (:body response) message))))

    (it "no longer has an open 0 spot when it has been selected by a player"
      (let [response (run-ring-app app (mock/request :post "/settings" param1)
                                       (mock/request :post "/play_game" {:spot 0}))]
        (should-not (clojure.string/includes? (:body response) "<td style='padding:0 15px 0 15px;'>0</td>"))))

    (it "AI chooses spot with parameter input"
      (let [response (run-ring-app app (mock/request :post "/settings" param1)
                                       (mock/request :post "/play_game" {:spot 4})
                                       (mock/request :post "/play_game"))]
        (should-not (clojure.string/includes? (:body response) "<td style='padding:0 15px 0 15px;'>8</td>"))))

    (it "first selected spot is replaced by the first player marker"
      (let [response (run-ring-app app (mock/request :post "/settings" param1)
                                       (mock/request :post "/play_game" {:spot 0}))]
        (should-not (clojure.string/includes? (:body response) (str "<td style='padding:0 15px 0 15px;'>" (:first-player param1)"</td>")))))

    (it "shows current marker message of the first player after two turns"
      (let [response (run-ring-app app (mock/request :post "/settings" param1)
                                       (mock/request :post "/play_game" {:spot 0})
                                       (mock/request :post "/play_game"))
            message (translate (loc) :output/current-player-marker (:player-one-marker param1))]
        (should (clojure.string/includes? (:body response) message)))))


  (context "game over"

    (def param2 {:player-one-marker "X"
                 :player-two-marker "O"
                 :player-one-name "Bob"
                 :player-two-name "Jane"
                 :player-one-type "Human"
                 :player-two-type "Human"
                 :first-player "player-one-marker"
                 :board-dimension "3"})

    (around [it]
      (with-redefs [data-storage (atom (->JSON))]
          (alternate-file-name @data-storage "test")
      (it)))

    (it "shows that player one has won when they have won"
      (let [response (run-ring-app app (mock/request :post "/settings" param2)
                                       (mock/request :post "/play_game" {:spot 0})
                                       (mock/request :post "/play_game" {:spot 1})
                                       (mock/request :post "/play_game" {:spot 3})
                                       (mock/request :post "/play_game" {:spot 5})
                                       (mock/request :post "/play_game" {:spot 6}))
            message (translate (loc) :output/player-one-has-won)]
        (should (clojure.string/includes? (:body response) message))))

    (it "shows that player two has won when they have won"
      (let [response (run-ring-app app (mock/request :post "/settings" param2)
                                       (mock/request :post "/play_game" {:spot 0})
                                       (mock/request :post "/play_game" {:spot 1})
                                       (mock/request :post "/play_game" {:spot 6})
                                       (mock/request :post "/play_game" {:spot 4})
                                       (mock/request :post "/play_game" {:spot 5})
                                       (mock/request :post "/play_game" {:spot 7}))
            message (translate (loc) :output/player-two-has-won)]
        (should (clojure.string/includes? (:body response) message))))

    (it "shows that game has been tied when it has been tied"
      (let [response (run-ring-app app (mock/request :post "/settings" param2)
                                       (mock/request :post "/play_game" {:spot 1})
                                       (mock/request :post "/play_game" {:spot 0})
                                       (mock/request :post "/play_game" {:spot 2})
                                       (mock/request :post "/play_game" {:spot 4})
                                       (mock/request :post "/play_game" {:spot 3})
                                       (mock/request :post "/play_game" {:spot 5})
                                       (mock/request :post "/play_game" {:spot 7})
                                       (mock/request :post "/play_game" {:spot 6})
                                       (mock/request :post "/play_game" {:spot 8}))
            message (translate (loc) :output/game-has-been-tied)]
        (should (clojure.string/includes? (:body response) message))))))

(describe "/scores"

  (around [it]
    (with-redefs [data-storage (atom (->JSON))] (it)))

  (it "fulfills response"
    (let [response (app (mock/request :get "/scores"))]
      (should= 200 (:status response))))

  (it "shows scores header"
    (let [response (app (mock/request :get "/scores"))
          message (translate (loc) :web/game-scores)]
      (should (clojure.string/includes? (:body response) message)))))

(describe "/not_found"

  (it "gives a 404 response"
    (let [response (app (mock/request :get "/not_found"))]
      (should= 404 (:status response))))

  (it "displays not found message"
    (let [response (app (mock/request :get "/not_found"))
          message (translate (loc) :web/sorry-page-not-found)]
      (should (clojure.string/includes? (:body response) message)))))
