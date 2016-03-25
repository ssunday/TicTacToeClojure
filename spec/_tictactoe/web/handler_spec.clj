(ns -tictactoe.web.handler-spec
  (:require [speclj.core :refer :all]
            [ring.mock.request :as mock]
            [-tictactoe.web.handler :refer :all]))

(describe "/"
  (it "fulfills response"
    (let [response (app (mock/request :get "/"))]
      (should= 200 (:status response)))))
