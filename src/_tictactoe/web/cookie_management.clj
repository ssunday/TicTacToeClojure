(ns -tictactoe.web.cookie_management
  (:require [noir.cookies :as cookie]
            [cheshire.core :as json]))

(defn cookie-exists []
  (not (nil? (cookie/get :data))))

(defn create-initial-cookie [params]
  (cookie/put! :data (json/generate-string params)))

(defn get-cookie-params []
  (json/parse-string (cookie/get :data) true))
