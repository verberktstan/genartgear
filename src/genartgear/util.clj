(ns genartgear.util
  (:require [genartlib.algebra :as a]
            [genartlib.util :as u]
            [quil.core :as q]))

;; TODO: Revamp this with respect to point maps (see below)
(defn point?
  [point]
  (every? number? point))

(defn line?
  [line]
  (and (every? coll? line)
       (every? point? line)))

(defn collision?
  "Returns a function that returns true if point collides with another point, with respect to the margin."
  ([point line-or-point]
   (collision? point (u/w 1/100) line-or-point))
  ([point margin [a b :as line-or-point]]
   (cond (line? line-or-point)
         (< (a/point-to-line-dist a b point) margin)
         (point? line-or-point)
         (< (a/point-dist point line-or-point) margin))))

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

(defn tanh-curve
  "Map input along a curve."
  [{:keys [negate? factor]
    :or {factor 1}}
  input]
  (-> factor pos? assert)
  (-> input number? assert)
  (cond-> input
    negate? dec
    factor  (* factor)
    :always clojure.math/tanh
    negate? inc))

;;;;; On polar & cartrsisn 2D points

(defn cartesian
  [{:keys [x y angle magnitude] :or {x 0 y 0} :as point}]
  (let [polar? (every? number? [angle magnitude])
        coord  (when polar?
                 (zipmap [:x :y] (a/angular-coords x y angle magnitude)))]
  (cond-> point polar? (merge coord))))

(defn point
  ([m] (point (select-keys m [:x :y]) (select-keys x [:angle :magnitude])))
  ([a b]
    (let [coord (-> a cartesian (select-keys [:x :y]))] 
      (merge-with + coord (cartesian b))))))))

(def xy (juxt :x :y))