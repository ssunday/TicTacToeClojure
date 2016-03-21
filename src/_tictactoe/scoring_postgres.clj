;Responsible for Postgres database data persistence.

(ns -tictactoe.scoring_postgres
  (:refer-clojure :exclude [update])
  (:require [-tictactoe.scoring_repository :as repository]
            [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json])
  (:use korma.core
        korma.db))

(def user-name (System/getProperty "user.name"))

(defdb db (postgres {:db user-name
                     :user user-name
                     :password ""
                     :host "localhost"
                     :port "5432"}))

(def db-start {:subprotocol "postgresql"
               :subname (str "//localhost:5432/" user-name)
               :user user-name
               :password ""})

(def table-name (keyword "tallys"))

(try (jdbc/db-do-commands db-start (jdbc/create-table-ddl table-name
                                                          [:name "varchar(32)"]
                                                          [:scores "varchar(32)"]))
(catch Exception e nil))

(declare tallys)

(defentity tallys
  (pk :id)
  (table table-name)
  (entity-fields :name :scores)
  (transform (fn [{scores :scores :as v}]
               (if scores
                 (assoc v :scores (json/parse-string scores true)) v)))
  (prepare (fn [{scores :scores :as v}]
              (if scores
                (assoc v :scores (json/generate-string scores)) v))))

(defmethod repository/alternate-file-name :pg [file] nil)

(defmethod repository/clear-all-data :pg [file]
  (delete tallys))

(defmethod repository/record-player-tallys :pg [player-tally]
  (doall (map #(insert tallys (values [{:name (first %) :scores (second %)}])) (:tally player-tally))))

(defn- names-and-scores []
  (select tallys (fields :name :scores)))

(defn- formatted-tallys []
  (map #(vector (:name %) (:scores %)) (names-and-scores)))

(defmethod repository/read-tally :pg [data]
  (let [tallys (formatted-tallys)]
    (if (not (empty? tallys))
        tallys)))
