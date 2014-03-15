;;; ANN for bool function (and A (not B))

(ns ann.ab
  (:require [clojure.math.numeric-tower :as math]))

(defn sigmoid
  "Logistic function"
  [x]
  (/ 1 (+ 1 (math/expt x))))

(println (sigmoid 123))
