{:dev  {:plugins [[jonase/eastwood "0.2.3"]]
        :eastwood {:linters [:all]
                   :namespaces [:source-paths]}
        :env {:database-name "tictactoe"}}
 :test {:dependencies [[speclj "3.3.1"]]
        :env {:database-name "test"}}}
