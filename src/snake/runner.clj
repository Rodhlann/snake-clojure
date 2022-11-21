(ns snake.runner
  (:require [clojure.core.async :refer [<! timeout chan go go-loop offer! poll!]]
            [snake.config :refer [height width]])
  (:import jline.Terminal))

(defn init-snake
  []
  [(int (quot width 1.25)),
   (int (quot height 1.25)),
  ;; TODO: randomize starting location
  ;; [rand-int 0 (int (quot width 1.25)),
  ;;  rand-int 0 (int (quot height 1.25)),
   119])

(def input-channel (chan))

; TODO: don't stop on input, we just want it to passively grab last pressed key
(defn watch-input []
  (go-loop []
    (<! (timeout 1000))
    (let [term (Terminal/getTerminal)]
     (offer! input-channel (.readCharacter term System/in)))
    (recur)))

(defn take-input []
  (poll! input-channel))

(defn update-location [key, snake]
  (println key)
  (case key
    ; TODO: Stop user from redirecting to opposite of current direction
    119 [(get snake 0), (- (get snake 1) 1), key] ;w
    115 [(get snake 0), (+ (get snake 1) 1), key] ;s
    97  [(- (get snake 0) 1), (get snake 1), key] ;a
    100 [(+ (get snake 0) 1), (get snake 1), key] ;d
    (update-location (get snake 2) snake)))

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
   (update-location
    (take-input)
    snake)))