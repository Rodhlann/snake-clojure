(ns snake.core
  (:require [snake.runner :refer [init-snake update-snake watch-input]])
  (:gen-class))

(defn -main
  [& args]
  (watch-input)
  (loop [snake (init-snake)]
    (println snake)
    (when (seq snake)
      (recur (update-snake snake))))
  (println "Game Over"))
