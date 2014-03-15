;;;; ANN for bool function (and A (not B))

;;; NS

(ns ann.ab
  (:require [ann.util :as util]))

;; 学习速率
;; 此参数可以缓和权重调整，不宜过大
(def study-rate 0.001)

(defn sgn
  [x]
  (if (> x 0) 1 -1))

;;; 初始化感知器

(defn rand-weight
  []
  (- (rand 2) 1))

(defn create-perceptron
  "创建感知器，随机生成 w0, w1, w2"
  []
  [(rand-weight)
   (rand-weight)
   (rand-weight)])

;; (def perceptron (create-perceptron))
(def perceptron (list 0 0 0))

;; (def a [1 2 3]) 
;; (def b [4 5 6])
;; (map * a b)
;; => (4 10 18)

(defn calc
  "w0 + w1*x1 + w2*x2"
  [inputs weights]
  (+ (apply + (map * inputs (rest weights)))
     (first weights)))

(defn good-enough?
  [delta]
  (< delta 0.1))

(defn could-not-do-better?
  [old-weights weights]
  (= old-weights weights))

(defn train-perceptron
  "训练感知器，返回新的 weights"
  [{inputs :input, target :output, weights :weights}]
  (def delta (- target (calc inputs weights)))
  (map (fn [input weight]
         (+ weight
            (* delta (sgn input) study-rate)))
       (concat '(1) inputs)
       weights))

;;; 对外接口

(defn train
  "Train NeuralNetwork"
  [{input :input, output :output}]
  (println (str "Train | Input: " input))
  (println (str "Train | Output: " output))
  (dotimes [i 10000]
    (def perceptron (train-perceptron {:input input, :output output, :weights perceptron}))))

(defn run
  ""
  [inputs]
  (def output (calc inputs perceptron))
  (println (str "Run | Input: " inputs))
  (println (str "Run | Output: " output)))

;;; Test

;; Train
(train {:input '(1 1) :output -1})
(train {:input '(1 -1) :output 1})
(train {:input '(-1 1) :output -1})
(train {:input '(-1 -1) :output -1})

;; Run
(run '(1 1))
(run '(1 -1))
(run '(-1 1))
(run '(-1 -1))
