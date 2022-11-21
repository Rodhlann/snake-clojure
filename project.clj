(defproject snake "0.1.0-SNAPSHOT"
  :description "Snake Game - Clojure"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [jline "0.9.94"]
                 [org.clojure/core.async "1.6.673"]]
  :main snake.core
  :aot [snake.core])