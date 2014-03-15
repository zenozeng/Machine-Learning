;;; ANN for bool function (and A (not B))

(ns ann.ab
  (:require [ann.util :as util]))

(def i 100)
(while (> i 0)
  (def i (dec i))
  (println i)
  (println (util/sigmoid i))
  )
