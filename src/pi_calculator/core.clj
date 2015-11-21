(ns pi-calculator.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan]])
  (:gen-class))

(def chunk-size 400)
(def sums (chan chunk-size))
(def results (atom []))

(defn build-range [start]
  (range (+ 1 (* chunk-size start)) (+ 1 (* chunk-size (+ 1 start)))))

(defn sum-a-chunk [seq]
  (double (reduce + (map (fn[e] (/ (- 1 (* 2 (rem e 2))) (+ 1 (* 2 e)))) seq))))

(defn calc-a-chunk [start]
  (go (>! sums (sum-a-chunk (build-range start)))))

(defn calc []
  (dotimes [n chunk-size]
    (calc-a-chunk n))
  (dotimes [n chunk-size]
    (swap! results (fn[current-state] (conj current-state (<!! sums)))))
  (* 4 (+ 1 (reduce + @result))))

(defn -main
  "Pi Calculation."
  [& args]
  (println "Starting...")
  (println (calc))
  (println "Done!"))
