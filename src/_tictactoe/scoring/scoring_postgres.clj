;Responsible for Postgres database data persistence.

(ns -tictactoe.scoring.scoring_postgres
  (:refer-clojure :exclude [update])
  (:require [-tictactoe.ttt.scoring_repository :as repository]
            [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json]
            [korma.core :refer :all]
            [korma.db :refer :all]
            [environ.core :refer [env]]))

(def user-name (System/getProperty "user.name"))

(def database-name (env :database-name))

(defdb db (postgres {:db database-name
                     :user user-name
                     :password ""
                     :host "localhost"
                     :port "5432"}))

(def db-start {:subprotocol "postgresql"
               :subname (str "//localhost:5432/" database-name)
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

(defn- clear-database []
  (delete tallys))

(defn- record-the-tallys [tally]
  (doall (map #(insert tallys (values [{:name (first %) :scores (second %)}])) tally)))

(defn- names-and-scores []
  (select tallys (fields :name :scores)))

(defn- formatted-tallys []
  (map #(vector (:name %) (:scores %)) (names-and-scores)))

(defn- read-the-tallys []
  (let [tallys (formatted-tallys)]
    (if (not (empty? tallys))
        tallys)))

(defrecord POSTGRES [] repository/DataType
  (repository/alternate-file-name [this name] nil)
  (repository/clear-all-data [this] (clear-database))
  (repository/read-tally [this] (read-the-tallys))
  (repository/record-player-tallys [this tally] (record-the-tallys tally)))
