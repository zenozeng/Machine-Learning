;;;; ANN for bool function (and A (not B))

;;; NS

(ns ann.ab
  (:require [ann.util :as util]))

;; Lib

;; (def log println)
(defn log [& body] nil)

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
  (let [result (< (abs delta) 0.1)]
    (log "Good-enough?" delta result)
    result))

(defn calc
  "w0 + w1*x1 + w2*x2"
  [inputs weights]
  (let [result (+ (apply + (map * inputs (rest weights)))
                  (first weights))]
    (log "Calc:" inputs weights result)
    result))

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

  (defn iter
    [perceptron data-collection]
    (defn all-good-enough?
      []
      (every? true?
              (map (fn [{inputs :inputs target :target}]
                     (good-enough? (- target (calc inputs perceptron))))
                   data-collection)))
    (doseq [{inputs :inputs target :target} data-collection]
      (def perceptron (train-perceptron perceptron inputs target)))
    (if (all-good-enough?)
      perceptron
      (iter perceptron data-collection)))

  (def perceptron (iter (create-perceptron) data-collection))

  ;; Test

  (defn gen
    [inputs]
    ; (println (calc inputs perceptron))
    )

  (gen [1 1])
  (gen [1 -1])
  (gen [-1 1])
  (gen [-1 -1]))
