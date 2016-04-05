(ns -tictactoe.web.session_management
  (:require [noir.session :as session]))

(defn get-session-value [key]
  (session/get key))

(defn set-session-value [key value]
  (session/put! key value))

(defn create-initial-session [params]
  (doall (map #(session/put! (key %) (val %)) params)))
