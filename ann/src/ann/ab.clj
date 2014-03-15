;;; ANN for bool function (and A (not B))

(ns ann.ab
  (:require [ann.util :as util]))

;; 学习速率
;; 此参数可以缓和权重调整，不宜过大
(def study-rate 0.01)

(defn train
  "Train NeuralNetwork"
  [{input :input, output :output}]
  (println (str "Train | Input: " input))
  (println (str "Train | Output: " output)))

(defn run
  ""
  [input]
  (def output input)
  (println (str "Run | Input: " input))
  (println (str "Run | Output: " output))
  )

(train {:input '(1 1) :output 0})
(train {:input '(1 0) :output 1})
(train {:input '(0 1) :output 0})
(train {:input '(0 0) :output 0})

(run '(1 1))
(run '(1 0))
(run '(0 1))
(run '(0 0))
