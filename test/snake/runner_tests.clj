(ns snake.runner-tests
  (:require [clojure.test :refer [deftest is testing]]
            [snake.config :refer [down left right up]]
            [snake.runner :refer [update-location]]))

(def snake-up [1, 1, up])
(def snake-down [1, 1, down])
(def snake-left [1, 1, left])
(def snake-right [1, 1, right])

(def snake-up-result [1, 0, up])
(def snake-down-result [1, 2, down])
(def snake-left-result [0, 1, left])
(def snake-right-result [2, 1, right])

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