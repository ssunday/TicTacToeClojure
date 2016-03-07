(defproject -tictactoe "0.1.0-SNAPSHOT"
  :description "Tic Tac Toe Command line game written in Clojure"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.7.0"]
                  [com.taoensso/tower "3.0.2"]]
  :main -tictactoe.core
  :profiles {:dev {:dependencies [[speclj "3.3.1"]]}}
  :plugins [[speclj "3.3.1"]]
  :test-paths ["spec"])
