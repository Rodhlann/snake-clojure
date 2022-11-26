(ns snake.runner
  (:require [clojure.core.async :refer [<!! >! chan go go poll! timeout]]
            [snake.config :refer [down height left tail-length right up width]])
  (:import jline.Terminal)
  (:gen-class))

(def input-channel (chan 1))

(defn init-snake []
  [(int (quot width 1.25)),    ;; Default x coord
   (int (quot height 1.25)),   ;; Default y coord
   up,                         ;; Default dir = up
   tail-length,                ;; Default tail-length value
   []])                        ;; Default empty tail

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

(defn update-snake [x, y, key, snake]
  (let [tail-length (get snake 3)]
    [x, y, key, tail-length, 
     (or 
      (take tail-length (into (get snake 4) [[(get snake 0), (get snake 1)]])) 
      [])]))

(defn update-location [key, snake]
  (println (str "key: " key))
  (cond
    (and (= key up) (not= (get snake 2) down)) (update-snake (get snake 0) (- (get snake 1) 1) key snake)
    (and (= key down) (not= (get snake 2) up)) (update-snake (get snake 0) (+ (get snake 1) 1) key snake)
    (and (= key left) (not= (get snake 2) right)) (update-snake (- (get snake 0) 1) (get snake 1) key snake)
    (and (= key right) (not= (get snake 2) left)) (update-snake (+ (get snake 0) 1) (get snake 1) key snake)
    :else (update-location (get snake 2) snake)))

(defn check-tail-collision [snake]
  (println snake)
  (let [coord [(get snake 0), (get snake 1)]
        tail (get snake 4)]
    (some #{coord} tail)))

(defn check-wall-collision [snake]
  (or
   (> (get snake 0) width)
   (< (get snake 0) 0)
   (> (get snake 1) height)
   (< (get snake 1) 0)))

(defn game-over [snake]
  (if
   (or
    (check-wall-collision snake)
    (check-tail-collision snake)
    (nil? (get snake 0))) ;; TODO: clarify this check 
    [] snake))

(defn snake-runner [snake]
  (game-over
   (update-location (take-input) snake)))
