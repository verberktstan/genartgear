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
  ([] (clip 0))
  ([lo] (fn clip* [input] (some-> input (max lo))))
  ([lo hi] (fn clip* [input] (some-> input (max lo) (min hi))))
  ([input lo hi] ((clip lo hi) input)))

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

(defn lag
  "Returns num `x` with - up to - positive num `rate` added or subtracted, in order to reach num `target`.
   `(lag 0.1 1 0.2) => 0.3`"
  ([x target rate]
    (lag x target rate rate))
  ([x target rate-up rate-down]
    (let [nums? (every? number? [x target])
          rates? (every? pos? [rate-up rate-down])]
    (cond-> x
      (and nums? rates? (< x target))
      (+ (-> target (- x) (min rate-up)))
      (and nums? rates? (> x target))
      (- (-> x (- target) (min rate-down)))))))