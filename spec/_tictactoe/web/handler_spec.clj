(ns -tictactoe.web.handler-spec
  (:require [speclj.core :refer :all]
            [ring.mock.request :as mock]
            [-tictactoe.scoring.scoring_json :refer :all]
            [-tictactoe.web.handler :refer :all])
  (:use [-tictactoe.ttt.localization :only (translate)]
        [-tictactoe.ttt.locale :only (loc)]))

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
      (should (clojure.string/includes? (:body response) message))))

)

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
            message (translate (loc) :web/error-markers-or-names-are-same)]
        (should-not (clojure.string/includes? (:body response) message))))

    (it "shows select marker information"
      (let [response (app (mock/request :get "/settings"))
            message (translate (loc) :web/select-unique-markers)]
        (should (clojure.string/includes? (:body response) message))))

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

    (it "does show bad-marker-input error message when markers are the name"
      (let [response (app (mock/request :post "/settings" invalid-params))
            message (translate (loc) :web/error-markers-or-names-are-same)]
        (should (clojure.string/includes? (:body response) message))))))

(describe "/play_game"
  (context "GET"
    (it "fulfills response"
      (let [response (app (mock/request :get "/play_game"))]
        (should= 200 (:status response))))))

(describe "/see_scores"

  (around [it]
    (with-redefs [data-storage (atom (->JSON))] (it)))

  (it "fulfills response"
    (let [response (app (mock/request :get "/see_scores"))]
      (should= 200 (:status response))))

  (it "shows scores header"
    (let [response (app (mock/request :get "/see_scores"))
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
