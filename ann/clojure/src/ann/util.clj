(ns ann.util
  (:require [clojure.math.numeric-tower :as math]))

(def E (. Math E))

(defn sigmoid
  "Logistic function"
  [x]
  (/ 1 (+ 1 (math/expt E (- x)))))
