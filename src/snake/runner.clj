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

(defn update-location [key, snake]
  (println (str "key: " key))
  (cond
    ;; TODO: Update tail-length value to not be hard-coded 0
    ;; TODO: Partition tail based on tail-length value
    ;; TODO: Break this out into separate sub-functions
    (and (= key up) (not= (get snake 2) down)) [(get snake 0), (- (get snake 1) 1), key, 0, (into (get snake 4) [[(get snake 0), (get snake 1)]])]
    (and (= key down) (not= (get snake 2) up)) [(get snake 0), (+ (get snake 1) 1), key, 0, (into (get snake 4) [[(get snake 0), (get snake 1)]])]
    (and (= key left) (not= (get snake 2) right)) [(- (get snake 0) 1), (get snake 1), key, 0, (into (get snake 4) [[(get snake 0), (get snake 1)]])]
    (and (= key right) (not= (get snake 2) left)) [(+ (get snake 0) 1), (get snake 1), key, 0, (into (get snake 4) [[(get snake 0), (get snake 1)]])]
    :else (update-location (get snake 2) snake)))

(defn check-tail-collision [snake]
  (println snake)
  (let [coord [(get snake 0), (get snake 1)]
        tail (get snake 4)]
    (some? (some #{coord} tail))))

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

(defn update-snake [snake]
  (game-over
   (update-location (take-input) snake)))
