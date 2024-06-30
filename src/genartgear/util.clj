(ns genartgear.util
  (:require
   [easings.core :as ease]
   [quil.core :as q]))

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

(def ^:private EASINGS
  {:in-sine      ease/ease-in-sine
   :out-sine     ease/ease-out-sine
   :in-out-sine  ease/ease-in-out-sine
   :in-quad      ease/ease-in-quad
   :out-quad     ease/ease-out-quad
   :in-out-quad  ease/ease-in-out-quad
   :in-cubic     ease/ease-in-cubic
   :out-cubic    ease/ease-out-cubic
   :in-out-cubic ease/ease-in-out-cubic
   :in-quart     ease/ease-in-quart
   :out-quart    ease/ease-out-quart
   :in-out-quart ease/ease-in-out-quart
   :in-quint     ease/ease-in-quint
   :out-quint    ease/ease-out-quint
   :in-out-quint ease/ease-in-out-quint
   :in-expo      ease/ease-in-expo
   :out-expo     ease/ease-out-expo
   :in-out-expo  ease/ease-in-out-expo
   :in-circ      ease/ease-in-circ
   :out-circ     ease/ease-out-circ
   :in-out-circ  ease/ease-in-out-circ
   :in-back      ease/ease-in-back
   :out-back     ease/ease-out-back
   :in-out-back  ease/ease-in-out-back})

(defn interpolate
  "Calculates a number between two numbers at a specific increment. The amt
  parameter is the amount to interpolate between the two values where 0.0 equal
  to the first point, 0.1 is very near the first point, 0.5 is half-way in
  between, etc.
  When you supply `:ease` keyword, the mapping is not linear but follows the
  easing functions. 
  The interpolate function is convenient for creating motion along a straight or
  eased path."
  [{:keys [start stop ease]
    :or {start 0 stop 1 ease :lerp}} amount]
  (let [ease-fn (get EASINGS ease)]
    (cond->> amount
      ease-fn ease-fn
      :always (q/lerp start stop))))

(comment
  (interpolate nil 0.15)
  (interpolate {:ease :in-sine} 0.15)
  (interpolate {:ease :out-quad} 0.15)
  (interpolate {:ease :in-out-cubic} 0.15)
  (interpolate {:ease :in-quart} 0.15)
  (interpolate {:ease :out-quint} 0.15)
  (interpolate {:ease :in-out-expo} 0.15)
  (interpolate {:ease :in-circ} 0.15)
  (interpolate {:ease :out-back} 0.15))
