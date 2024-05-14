(ns genartgear.color
  (:require [genartgear.util :refer [clip wrap]]))

;; All colors are defined in HSBA
;; Hue [0, 360]
;; Saturation [0, 100]
;; Brightness [0, 100]
;; Alpha [0, 1]

(def HUE 0)
(def SATURATION 1)
(def BRIGHTNESS 2)
(def ALPHA 3)

;; https://www.w3schools.com/colors/colors_crayola.asp
(def CRAYOLA
  (merge
    #:crayola{:vivid-violet [289.21, 61.81, 56.47, 1]}
    #:fluorescent{:atomic-tangerine  [20, 60, 100, 1]
                  :electric-lime     [72, 100, 100, 1]
                  :outrageous-orange [12.3, 78.43, 100, 1]
                  :radical-red       [347.8, 79.22, 100, 1]
                  :blizzard-blue     [195.6, 65.22, 90.2, 1]
                  :hot-magenta       [312, 100, 100, 1]
                  :laser-lemon       [60, 60, 100, 1]
                  :magic-mint        [153.43, 29.17, 94.12, 1]
                  :neon-carrot       [30, 80, 100, 1]
                  :razzle-dazzle-rose [309.03, 78.15, 93.33, 1]
                  :screamin-green    [120, 60, 100, 1]
                  :shocking-pink     [300, 56.86, 100, 1]
                  :sunglow           [45, 80, 100, 1]
                  :wild-watermelon   [349.26, 64.03, 99.22, 1]}
    #:bright{:sizzling-red [351.26, 78.04, 100, 1]
             :red-salsa    [355.08, 77.08, 99.22, 1]
             :tart-orange  [2.32, 72.11, 98.43, 1] ; #FB4D46
             :orange-soda  [9.52, 75.6, 98.04, 1]
             :bright-yellow [37.43, 88.63, 100, 1] ; #FFAA1D
             :yellow-sunshine [58.12, 100, 100, 1] ; #FFF700
             :slimy-green  [111.5, 84.67, 58.82, 1] ; #299617
             :green-lizard [83.81, 79.51, 95.69, 1] ; #A7F432
             :denim-blue   [226.62, 81.32, 71.37, 1]
             :spring-frost [93.8, 83.53, 100, 1]}
    #:metallic{:bdazzled-blue       [215.29, 68.92, 58.04, 1]
               :seaweed             [186.46, 92.86, 54.9, 1]
               :bittersweet-shimmer [358.93, 58.64, 74.9, 1]}))

(defn update-hue [[h s b a :as color] f & args]
  (assert (vector? color))
  (let [f' (comp (wrap 0 360) f)]
    (if h
      (apply update color HUE (comp (wrap 0 360) f) args)
      (vector (apply f' 0 args) (or s 100) (or b 100) (or a 1)))))

(defn update-saturation [[h s b a :as color] f & args]
  (assert (vector? color))
  (let [f' (comp (clip 0 100) f)]
    (if s
      (apply update color SATURATION (comp (clip 0 100) f) args)
      (vector h (apply f' 100 args) (or b 100) (or a 1)))))

(defn update-brightness [[h s b a :as color] f & args]
  (assert (vector? color))
  (let [f' (comp (clip 0 100) f)]
    (if b
      (apply update color BRIGHTNESS f' args)
      (vector h s (apply f' 100 args) (or a 1)))))

(defn update-alpha [[h s b a :as color] f & args]
  (assert (vector? color))
  (let [f' (comp (clip 0.0 1.0) f)]
    (if a
      (apply update color ALPHA f' args)
      (vector h s b (apply f' 1 args)))))
