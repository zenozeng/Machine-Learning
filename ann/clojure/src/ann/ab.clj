;;;; ANN for bool function (and A (not B))

;;; NS

(ns ann.ab
  (:require [ann.util :as util]))

;; Lib

(def log println)
;;(defn log [& body] nil)

(defn abs
  [n]
  (if (> n 0) n (- n)))

;; 学习速率
;; 此参数可以缓和权重调整，不宜过大
(def study-rate 0.1)

(defn sgn
  [x]
  (if (> x 0) 1 -1))

(defn good-enough?
  [delta]
  (< (abs delta) 0.9))

(defn calc
  "w0 + w1*x1 + w2*x2"
  [inputs weights]
  (+ (apply + (map * inputs (rest weights)))
     (first weights)))

;;; 感知器

(defn create-perceptron
  []
  [0 0 0])

(defn train-perceptron
  "训练感知器，返回新的感知器"
  [perceptron inputs target]
  (let [delta (- target (calc inputs perceptron))]
    (map (fn [input weight]
           (+ weight
              (* delta (sgn input) study-rate)))
         (concat [1] inputs)
         perceptron)))

;;; Test

(let [data-collection [{:inputs [1 1] :target -1}
                       {:inputs [1 -1] :target 1}
                       {:inputs [-1 1] :target -1}
                       {:inputs [-1 -1] :target -1}]]
  ;; Train Data

  (defn all-good-enough?
    [perceptron]
    (every? true?
            (map (fn [{inputs :inputs target :target}]
                   (good-enough? (- target (calc inputs perceptron))))
                 data-collection)))

  (def perceptron (loop [perceptron (create-perceptron)]
                       (let [perceptron (loop [perceptron perceptron, data-collection data-collection]
                                       (if (first data-collection)
                                         (let [{inputs :inputs target :target} (first data-collection)]
                                           (recur (train-perceptron perceptron inputs target) (rest data-collection)))
                                         perceptron))]
                         (if (all-good-enough? perceptron)
                           perceptron
                           (recur perceptron)))))

  (log perceptron)

  ;; Test

  (defn gen
    [inputs]
    (log "TEST" inputs "=>" (sgn (calc inputs perceptron))))

  (gen [1 1])
  (gen [1 -1])
  (gen [-1 1])
  (gen [-1 -1]))
