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
    (when (seq snake)
      (recur (snake-runner snake))))
  (println "Game Over"))
