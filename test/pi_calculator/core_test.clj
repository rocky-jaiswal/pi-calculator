(ns pi-calculator.core-test
  (:require [clojure.test :refer :all]
            [pi-calculator.core :refer :all]))

(deftest test-range-build
  (testing "the range builder should work"
    (is (= (range 1 401) (build-range 0)))
    (is (= (range 401 801) (build-range 1)))))
