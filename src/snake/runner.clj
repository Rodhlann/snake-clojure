(ns snake.runner
  (:require [clojure.core.async :refer [<!! >! chan go go poll! timeout]]
            [snake.config :refer [down height left right up width]])
  (:import jline.Terminal)
  (:gen-class))

(def input-channel (chan 1))

(defn init-snake
  []
  [(int (quot width 1.25)),
   (int (quot height 1.25)),
   up]) ;; Default to up direction

;; TODO: figure out how to clear terminal buffer or only pick last char, currently if the user holds down a key it will continue reading that stream indefinitely
;; Maybe iterate through the entire terminal buffer, leaving only the latest option for the input-channel?
(defn watch-input []
  (<!! (timeout 1000))
  (go
    (let [term (Terminal/getTerminal)]
      (>! input-channel (.readCharacter term System/in)))))

(defn take-input []
  (println (str "buff: " (.count (.buf input-channel))))
  (poll! input-channel))

(defn update-location [key, snake]
  (println (str "key: " key))
  (cond
    (and (= key up) (not= (get snake 2) down)) [(get snake 0), (- (get snake 1) 1), key]
    (and (= key down) (not= (get snake 2) up)) [(get snake 0), (+ (get snake 1) 1), key]
    (and (= key left) (not= (get snake 2) right)) [(- (get snake 0) 1), (get snake 1), key]
    (and (= key right) (not= (get snake 2) left)) [(+ (get snake 0) 1), (get snake 1), key]
    :else (update-location (get snake 2) snake)))

(defn game-over [snake]
  (cond
    (nil? (get snake 0)) []
    (> (get snake 0) width) []
    (< (get snake 0) 0) []
    (> (get snake 1) height) []
    (< (get snake 1) 0) []
    :else snake))

(defn update-snake [snake]
  (game-over
   (update-location (take-input) snake)))
