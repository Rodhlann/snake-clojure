(ns snake.core
  (:require
   [snake.runner :refer [init-snake update-snake watch-input]])
  (:gen-class))

(defn -main
  [& args]
  (loop [snake (init-snake)]
    (println "-------")
    (println (str "snake: " snake))
    (watch-input)
    (when (seq snake)
      (recur (update-snake snake))))
  (println "Game Over"))
