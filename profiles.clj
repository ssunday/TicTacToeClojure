{:dev  {:dependencies [[javax.servlet/servlet-api "2.5"]
                      [ring/ring-mock "0.3.0"]]
        :env {:database-name "tictactoe"}}
 :test {:dependencies [[speclj "3.3.1"]]
        :plugins [[jonase/eastwood "0.2.3"]]
        :eastwood {:linters [:all]
                   :namespaces [:source-paths]}
        :env {:database-name "test"}}}
