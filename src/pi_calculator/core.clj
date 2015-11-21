(ns pi-calculator.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan]])
  (:gen-class))

(def x 10)
(def sums (chan x))
(def result (atom []))

(defn sum [n]
  (go
    (Thread/sleep (rand 100))
    (println n)
    (>! sums (rand-int 100))))

(defn calc []
  (dotimes [n x]
    (sum n))
  (dotimes [n x]
    (println "waiting...")
    (swap! result (fn [current-state] (conj current-state (<!! sums)))))
  (println @result))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (calc))
