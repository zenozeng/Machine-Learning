(ns ann.ann)

(defn create-perceptron
  "创建感知器，随机生成 w0, w1, w2"
  []
  (defn rand-weight
    []
    (- (rand 2) 1))
  [(rand-weight)
   (rand-weight)
   (rand-weight)])
