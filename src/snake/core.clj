(ns snake.core
  (:require
   [snake.runner :refer [init-snake snake-runner watch-input]])
  (:gen-class))

(defn -main
  [& args]
  (loop [snake (init-snake)]
    (println "-------")
    (println (str "snake: " snake))
    (watch-input)
    (if-not (vector? snake)
      (println (str "Game Over - Score: " snake))
      (recur (snake-runner snake)))))