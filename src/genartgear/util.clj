(ns genartgear.util)

(defn point?
  [point]
  (every? number? point))

(defn line?
  [line]
  (and (every? coll? line)
       (every? point? line)))

(defn clip
  "Returns a function that returns a clipped value between lo and hi. When input
  is given, clip returns the result immediately."
  ([lo hi]
   (comp (partial max lo) (partial min hi)))
  ([input lo hi]
   ((clip lo hi) input)))

(defn wrap
  "Returns a function that returns a wrapped value between lo and hi. When input
  is given, wrap returns the result immediately."
  ([lo hi]
   (assert (> hi lo))
   (let [diff (- hi lo)]
     (fn wrap* [input]
       (loop [x input]
         (if (<= lo x hi)
           x
           (recur (if (< x lo) (+ x diff) (- x diff))))))))
  ([input lo hi]
   ((wrap lo hi) input)))

(defn fold
  "Returns a function that returns a folded value between lo and hi. When input
  is given, wrap returns the result immediately."
  ([lo hi] (-> lo (< hi) assert)
  (fn fold* [input]
    (if (<= lo input hi)
      input
      (recur
        (if (> input hi)
          (- hi (- input hi))
          (+ lo (- lo input)))))))
  ([input lo hi] ((fold lo hi) input)))

#_(defn tanh-curve
  "Map input along a curve."
  [{:keys [negate? factor]
    :or {factor 1}}
  input]
  (-> factor pos? assert)
  (-> input number? assert)
  (cond-> input
    negate? dec
    factor (* factor)
    :always clojure.math/tanh
    negate? inc))