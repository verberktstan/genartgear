(ns genartgear.color)

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
    #:fluorescent{:outrageous-orange [12.3, 78.43, 100, 1]
                  :radical-red       [347.8, 79.22, 100, 1]
                  :blizzard-blue     [195.6, 65.22, 90.2, 1]
                  :screamin-green    [120, 60, 100, 1]}
    #:bright{:red-salsa    [355.08, 77.08, 99.22, 1]
             :orange-soda  [9.52, 75.6, 98.04, 1]
             :denim-blue   [226.62, 81.32, 71.37, 1]
             :spring-frost [93.8, 83.53, 100, 1]}
    #:metallic{:bdazzled-blue       [215.29, 68.92, 58.04, 1]
               :seaweed             [186.46, 92.86, 54.9, 1]
               :bittersweet-shimmer [358.93, 58.64, 74.9, 1]}))

(defn- clip [lo hi]
  (comp (partial max lo) (partial min hi)))

(defn- wrap [lo hi]
  (assert (> hi lo))
  (let [diff (- hi lo)]
    (fn wrap* [input]
      (loop [x input]
        (if (<= lo x hi)
          x
          (recur (if (< x lo) (+ x diff) (- x diff))))))))

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
