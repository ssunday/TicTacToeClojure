(ns -tictactoe.web.session_management-spec
  (:require [speclj.core :refer :all]
            [noir.session :as session]
            [compojure.core :as compojure]
            [compojure.handler :as handler]
            [ring.mock.request :as mock]
            [-tictactoe.web.session_management :refer :all])
  (:use [noir.session :only (wrap-noir-session)]
        [ring-test.core :only (run-ring-app)]))

(compojure/defroutes test-app-routes
  (compojure/GET "/create" request (create-initial-session (:params request)))
  (compojure/GET "/base-set" [] (session/put! :test "foo"))
  (compojure/GET "/base-get" [] {:status 200 :body (get-session-value :test)})
  (compojure/GET "/set" request (set-session-value (:key (:params request)) (:value (:params request))))
  (compojure/GET "/create-test" [] {:status 200 :body (get-session-value :board)})
  (compojure/GET "/get" request {:status 200 :body (get-session-value (:key (:params request)))}))

(def app (wrap-noir-session (handler/site test-app-routes)))

(def params1
  {:player-one-name "Sarah"
   :player-two-name "John"
   :player-one-marker "X"
   :player-two-marker "O"
   :player-one-is-ai true
   :player-two-is-ai false
   :board [0 1 2 3 4 5 6 7 8]
   :current-player "X"})

(describe "get-session-value"
  (it "gets session data given key"
    (let [response (run-ring-app app (mock/request :get "/base-set")
                                     (mock/request :get "/base-get"))]
      (should= "foo" (:body response)))))

(describe "set-session-value"
  (it "sets session data given key and value"
    (let [response (run-ring-app app (mock/request :get "/set" {:key :player-one-name :value "Sarah"})
                                     (mock/request :get "/get" {:key :player-one-name}))]
      (should= (:player-one-name params1) (:body response)))))

(describe "create-initial-session"
  (it "creates a session"
    (let [response (run-ring-app app (mock/request :get "/create" params1)
                                     (mock/request :get "/create-test"))]
      (should= (vec (map str [0 1 2 3 4 5 6 7 8])) (:body response)))))
