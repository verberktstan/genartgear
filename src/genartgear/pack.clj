(ns genartgear.pack
  (:require [genartlib.random :as r]
            [genartlib.util :as u]
            [quil.core :as q]))

(defn- overlap?
  [{xa  :circle/x ya :circle/y ra :circle/radius ma :circle/margin
    :or {ma 0}}]
  (fn overlap?* [{xb  :circle/x yb :circle/y rb :circle/radius mb :circle/margin
                  :or {mb 0}}]
    (< (q/dist xa ya xb yb) (+ ra rb ma mb))))

(defn- grow-circle
  [circle step]
  (update circle :circle/radius + step))

(defn- make-circle [{:keys [x y radius margin]}]
  (assert x)
  (assert y)
  (assert radius)
  (let [rx (/ x (u/w))
        ry (/ y (u/h))]
    #:circle{:x      x      :rx    rx
             :y      y      :ry    ry
             :radius radius :noise (q/noise rx ry)
             :margin margin}))

(defn circles
  [{:keys [random-point min-radius max-radius step max-n make-circle overlap? margin]
    :or   {random-point #(r/random-point-in-circle 0 0 100)
           max-n        10 ; Max number of circles to pack
           min-radius   10 ; Min radius of each circle
           max-radius   20 ; Max radius of each circle
           step         1 ; Step to increase
           overlap?     overlap?
           make-circle  make-circle
           margin       0}
    :as   props} coll]
  (assert (< min-radius max-radius))
  (assert (pos? step))
  (reduce
    (fn circles* [circles [x y :as point]]
      (loop [{:circle/keys [radius]
              :as          circle} (merge (make-circle {:x x :y y :radius min-radius :margin margin})
                                          {:noise (q/noise (/ x (u/w)) (/ y (u/h)))}
                                          props)]
        (let [overlap?? (overlap? circle)]
          (if (or (some overlap?? circles) (>= radius max-radius))
            (cond-> circles
              (::valid? circle) (conj (update circle :circle/radius #(min % max-radius))))
            (recur (-> circle (assoc ::valid? true) (grow-circle step)))))))
    (or coll nil)
    (repeatedly max-n random-point)))
