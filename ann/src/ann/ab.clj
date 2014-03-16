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
  (let [result (< (abs delta) 0.9)]
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
  (let [delta (- target (calc inputs perceptron))
        new-perceptron (map (fn [input weight]
                              (+ weight
                                 (* delta (sgn input) study-rate)))
                            (concat [1] inputs)
                            perceptron)]
    (log "New Perceptron:" new-perceptron)
    new-perceptron))

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

  (def my-perceptron (loop [perceptron (create-perceptron)]
                       (let [my-perc (loop [perc perceptron, data-collection data-collection]
                                       (if (first data-collection)
                                         (let [{inputs :inputs target :target} (first data-collection)]
                                           (recur (train-perceptron perc inputs target) (rest data-collection)))
                                         perc))]
                         (if (all-good-enough? my-perc)
                           my-perc
                           (recur my-perc)))))

  (log my-perceptron)

  ;; Test

  (defn gen
    [inputs]
    (println (sgn (calc inputs my-perceptron)))
    )

  (gen [1 1])
  (gen [1 -1])
  (gen [-1 1])
  (gen [-1 -1]))
