(ns snake.runner-tests
  (:require [clojure.test :refer [deftest is testing]]
            [snake.config :refer [down height left right tail-length up width]]
            [snake.runner :refer [check-tail-collision check-wall-collision
                                  update-location update-snake]]))

(def snake-up [1, 1, up, tail-length, []])
(def snake-down [1, 1, down, tail-length, []])
(def snake-left [1, 1, left, tail-length, []])
(def snake-right [1, 1, right, tail-length, []])

(def snake-up-result [1, 0, up, tail-length, []])
(def snake-down-result [1, 2, down, tail-length, []])
(def snake-left-result [0, 1, left, tail-length, []])
(def snake-right-result [2, 1, right, tail-length, []])

#_{:clj-kondo/ignore [:inline-def]}
(deftest update-location-unknown-test
  (testing "unknown input value"
    (is (= (update-location nil snake-up) snake-up-result))))

#_{:clj-kondo/ignore [:inline-def]}
(deftest update-location-up-test
  (testing "up"
    (is (= (update-location up snake-up) snake-up-result)))
  (testing "down"
    (is (= (update-location down snake-up) snake-up-result)))
  (testing "left"
    (is (= (update-location left snake-up) snake-left-result)))
  (testing "right"
    (is (= (update-location right snake-up) snake-right-result))))

#_{:clj-kondo/ignore [:inline-def]}
(deftest update-location-down-test
  (testing "up"
    (is (= (update-location up snake-down) snake-down-result)))
  (testing "down"
    (is (= (update-location down snake-down) snake-down-result)))
  (testing "left"
    (is (= (update-location left snake-down) snake-left-result)))
  (testing "right"
    (is (= (update-location right snake-down) snake-right-result))))

#_{:clj-kondo/ignore [:inline-def]}
(deftest update-location-left-test
  (testing "up"
    (is (= (update-location up snake-left) snake-up-result)))
  (testing "down"
    (is (= (update-location down snake-left) snake-down-result)))
  (testing "left"
    (is (= (update-location left snake-left) snake-left-result)))
  (testing "right"
    (is (= (update-location right snake-left) snake-left-result))))

#_{:clj-kondo/ignore [:inline-def]}
(deftest update-location-right-test
  (testing "up"
    (is (= (update-location up snake-right) snake-up-result)))
  (testing "down"
    (is (= (update-location down snake-right) snake-down-result)))
  (testing "left"
    (is (= (update-location left snake-right) snake-right-result)))
  (testing "right"
    (is (= (update-location right snake-right) snake-right-result))))

(deftest check-tail-collision-test
  (testing "no tail collision"
    (is (= (check-tail-collision [0, 0, up, tail-length, []]) nil)))
  (testing "tail collision"
    (is (= (check-tail-collision [0, 0, up, tail-length, [[0, 0]]]) [0, 0]))))

(deftest check-wall-collision-test
  (testing "up"
    (is (= (check-wall-collision [0, -1, up, tail-length, []]) true)))
  (testing "down"
    (is (= (check-wall-collision [0, (+ height 1), down, tail-length, []]) true)))
  (testing "left"
    (is (= (check-wall-collision [-1, 0, left, tail-length, []]) true)))
  (testing "right"
    (is (= (check-wall-collision [(+ width 1), 0, right, tail-length, []]) true))))

(deftest update-snake-test
  (testing "tail-length 0"
    (is (= (update-snake 0, 1 up [0, 0, up, 0, []]) [0, 1, up, 0, []])))
  (testing "tail-length 1"
    (is (= (update-snake 0, 1 up [0, 0, up, 1, []]) [0, 1, up, 1, [[0, 0]]]))))