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
  ([point] (collision? point (u/w 1/100)))
  ([point margin]
   (fn [[a b :as line-or-point]]
     (cond (line? line-or-point)
           (< (a/point-to-line-dist a b point) margin)
           (point? line-or-point)
           (< (a/point-dist point line-or-point) margin)))))
