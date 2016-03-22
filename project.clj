(defproject -tictactoe "1.7.0-SNAPSHOT"
  :description "Tic Tac Toe Command line game written in Clojure"
  :url "https://github.com/ssunday/TicTacToeClojure"
  :dependencies [[org.clojure/clojure "1.7.0"]
                  [com.taoensso/tower "3.0.2"]
                  [cheshire "5.5.0"]
                  [korma "0.4.2"]
                  [postgresql/postgresql "9.1-901.jdbc4"]
                  [org.clojure/java.jdbc "0.3.7"]]
  :main -tictactoe.core
  :profiles {:dev {:dependencies [[speclj "3.3.1"]]
                   :plugins [[jonase/eastwood "0.2.3"]]
                   :eastwood {:linters [:all]
                              :namespaces [:source-paths]}}}
  :plugins [[speclj "3.3.1"]
            [lein-ns-dep-graph "0.1.0-SNAPSHOT"]]
  :test-paths ["spec"]
  :jvm-opts ~(vec (map (fn [[p v]] (str "-D" (name p) "=" v))
                       {"com.mchange.v2.log.MLog" "com.mchange.v2.log.FallbackMLog"
                        "com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL" "OFF"})))
