(ns genartgear.util
  (:require [genartlib.algebra :as a]
            [genartlib.util :as u]
            [quil.core :as q]))

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