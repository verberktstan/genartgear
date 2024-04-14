(ns genartgear.util
  (:require [genartlib.util :as u]
            [quil.core :as q]))

(defn collision?
  "Returns a function that returns true if point collides with another point, with respect to the margin."
  ([point] (collision? point (u/w 1/100)))
  ([point margin]
   (fn [another-point]
     (< (apply q/dist (concat point another-point)) margin))))
