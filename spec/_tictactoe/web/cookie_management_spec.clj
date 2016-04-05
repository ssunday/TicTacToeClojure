(ns -tictactoe.web.cookie_management-spec
  (:require [speclj.core :refer :all]
            [noir.cookies :as cookies]
            [compojure.core :as compojure]
            [compojure.handler :as handler]
            [ring.mock.request :as mock]
            [-tictactoe.web.cookie_management :refer :all])
  (:use [ring-test.core :only (run-ring-app)]))

(compojure/defroutes test-app-routes
  (compojure/GET "/set" [] (cookies/put! :data "value"))
  (compojure/GET "/create" request (create-initial-cookie (:params request)))
  (compojure/GET "/exists" [] {:status 200 :body (cookie-exists)})
  (compojure/GET "/get" [] {:status 200 :body (get-cookie-params)}))

(def app (cookies/wrap-noir-cookies (handler/site test-app-routes)))

(def params1
  {:player-one-name "Sarah"
   :player-two-name "John"
   :player-one-marker "X"
   :player-two-marker "O"
   :player-one-type "Human"
   :player-two-type "AI"
   :board-dimension "3"
   :first-player "player-one-marker"})

(describe "cookie-exists"

  (it "returns false when cookies does not exist"
    (let [response (run-ring-app app (mock/request :get "/exists"))]
      (should= false (:body response))))

  (it "returns true when cookies does exist"
    (let [response (run-ring-app app (mock/request :get "/set")
                                     (mock/request :get "/exists"))]
      (should= true (:body response)))))

(describe "create-initial-cookie"
  (it "creates a cookie"
    (let [response (run-ring-app app (mock/request :get "/create" params1)
                                     (mock/request :get "/exists"))]
      (should= true (:body response)))))

(describe "get-cookie-params"
  (it "gets data back out from cookie as it was put in"
    (let [response (run-ring-app app (mock/request :get "/create" params1)
                                     (mock/request :get "/get"))]
      (should= params1 (:body response)))))
