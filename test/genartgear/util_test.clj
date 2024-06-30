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
