(defproject -tictactoe "1.7.1-SNAPSHOT"
  :description "Tic Tac Toe Command line game written in Clojure"
  :url "https://github.com/ssunday/TicTacToeClojure"
  :dependencies [[org.clojure/clojure "1.8.0"]
                  [com.taoensso/tower "3.0.2"]
                  [cheshire "5.5.0"]
                  [korma "0.4.2"]
                  [postgresql/postgresql "9.1-901.jdbc4"]
                  [org.clojure/java.jdbc "0.3.7"]
                  [environ "1.0.2"]
                  [compojure "1.4.0"]
                  [ring "1.4.0"]
                  [stencil "0.5.0"]
                  [lib-noir "0.9.9"]
                  [ring-test "0.1.3"]]
  :main -tictactoe.core
  :plugins [[speclj "3.3.1"]
            [lein-environ "1.0.2"]
            [lein-ring "0.9.7"]
            [lein-ns-dep-graph "0.1.0-SNAPSHOT"]]
  :aliases {"spec" ["with-profile" "+test" "spec"]
            "eastwood" ["with-profile" "+test" "eastwood"]}
  :test-paths ["spec"]
  :jvm-opts ~(vec (map (fn [[p v]] (str "-D" (name p) "=" v))
                       {"com.mchange.v2.log.MLog" "com.mchange.v2.log.FallbackMLog"
                        "com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL" "OFF"})))
