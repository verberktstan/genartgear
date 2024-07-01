(ns genartgear.util-test
  (:require
   [clojure.test :refer [are deftest is]]
   [genartgear.util :as sut]))

(deftest round
  (is (= 0.12 (sut/round 0.1234)))
  (is (= 0.13 (sut/round 0.1254)))
  (are [precision result] (= result (sut/round 0.123456789 :precision precision))
    0 0
    1 0.1
    2 0.12
    3 0.123
    4 0.1235
    5 0.12346
    6 0.123457
    7 0.1234568
    8 0.12345679
    9 0.123456789))

(deftest spread
  (are [result inputs] (= result (map #(sut/round % :precision 4) (apply sut/spread inputs)))
    [0.25 0.75] [2]
    [0.1667 0.5 0.8333] [3]
    [0.1 0.3 0.5 0.7 0.9] [5]
    [0.0625 0.1875 0.3125 0.4375 0.5625 0.6875 0.8125 0.9375] [8])
  (is (thrown? AssertionError (sut/spread 0)))
  (is (thrown? AssertionError (sut/spread nil))))
