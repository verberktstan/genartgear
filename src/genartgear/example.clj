(ns genartgear.example
  (:require [genartgear.core :as gg]
            [quil.core :as q]
            [quil.middleware :as m]))

(defn- draw
  [{:keys [gray] :as props}]
  (println props)
  (q/background (or gray (q/random 360))))

;; Simply pass a draw function
(gg/sketch draw)

;; Supply additional arguments like :title & :size
;; (gg/sketch draw :title "another-sketch" :size [800 300])

;; Arguments passed to sketch will also be passed on to your draw fn.
;; (gg/sketch draw :gray 180)
