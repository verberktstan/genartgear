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
            :blue-jeans   [206.43, 60.59, 92.55, 1] ; #5DADEC
            :plump-purple [250.56, 60.67, 69.8, 1] ; #5946B2
            :purple-plum  [284.55, 55.49, 71.37, 1] ; #9C51B6
            :sweet-brown  [3.03, 70.83, 65.88, 1] ; #A83731
            :brown-sugar  [20.2, 56, 68.63, 1] ; #AF6E4D
            :eerie-black  [0, 0, 10.59, 1] ; #1B1B1B
            :black-shadows [348.75, 8.38, 74.9] ; #BFAFB2
            :fiery-rose   [350.18, 67.06, 100, 1] ; #FF5470
            :sizzling-sunrise [51.53, 100, 100, 1] ; #FFDB00
            :heat-wave    [28.71, 100, 100, 1] ; #FF7A00
            :lemon-glacier [60.47, 100, 100, 1] ; #FDFF00
            :spring-frost [93.8, 83.53, 100, 1]
            :absolute-zero [216.77, 100, 72.94, 1] ; #0048BA
            :winter-sky   [330.82, 100, 100, 1] ; #FF007C
            :frostbite    [322.12, 76.82, 91.37, 1] ; #E936A7
            }
   #:metallic{:alloy-orange        [27.33, 91.84, 76.86, 1] ; #C46210
              :bdazzled-blue       [215.29, 68.92, 58.04, 1]
              :big-drip-o-ruby     [345.38, 76.28, 61.18, 1] ; #9C2542
              :bittersweet-shimmer [358.93, 58.64, 74.9, 1]
              :blast-off-bronze    [12, 39.39, 64.71, 1] ; #A57164
              :cyber-grape         [262.76, 46.77, 48.63, 1] ; #58427C
              :deep-space-sparkle  [194.12, 31.48, 42.35, 1] ; #4A646C
              :gold-fusion         [42.55, 41.35, 52.16, 1] ; #85754E
              :illuminating-emerald [163.75, 66.21, 56.86, 1] ; #319177
              :metallic-seaweed    [186.46, 92.86, 54.9, 1] ; #0A7E8C
              :metallic-sunburst   [40.8, 64.1, 61.18, 1] ; #9C7C38
              :razzmic-berry       [307.62, 44.68, 55.29, 1] ; #8D4E85 
              :sheen-green         [79.53, 100, 83.14, 1] ; #8FD400
              :shimmering-blush    [349.16, 38.25, 85.1, 1] ; #D98695
              :sonic-silver        [0, 0, 45.88, 1] ; #757575 
              :steel-blue          [194.74, 100, 67.06, 1] ; #0081AB
              }
   #:silver{:aztec-gold      [37.5, 57.44, 76.47, 1] ; #C39953
            :burnished-brown [8, 27.95, 63.14, 1] ; #A17A74
            :cerulean-frost  [207.91, 44.1, 76.47, 1] ; #6D9BC3
            :cinnamon-satin  [343.49, 53.17, 80.39, 1] ; #CD607E
            :copper-penny    [5.29, 39.31, 67.84, 1] ; #AD6F69
            :cosmic-cobalt   [240.66, 66.91, 53.33, 1] ; #2E2D88
            :glossy-grape    [285.45, 18.44, 70.2 ,1] ; #AB92B3
            :granite-gray    [0, 0, 40.39, 1] ; #676767
            :green-sheen     [167.81, 36.78, 68.24, 1] ; #6EAEA1
            :lilac-luster    [310.91, 12.64, 68.24, 1] ; #AE98AA
            :misty-moss      [53.82, 36.36, 73.33, 1] ; #BBB477
            :mystic-maroon   [329.43, 61.27, 67.84, 1] ; #AD4379
            :pearly-purple   [315.95, 43.17, 71.76, 1] ; #B768A2
            :pewter-blue     [200.45, 24.04, 71.76, 1] ; #8BA8B7
            :polished-pine   [165.63, 43.29, 64.31, 1] ; #5DA493
            :quick-silver    [0, 0, 65.1, 1] ; #A6A6A6
            :rose-dust       [344.06, 40.51, 61.96, 1] ; #9E5E6F
            :rusty-red       [352.07, 79.82, 85.49, 1] ; #DA2C43
            :shadow-blue     [213.91, 27.88, 64.71, 1] ; #778BA5
            :shiny-shamrock  [140.83, 43.11, 65.49, 1] ; #5FA778
            :steel-teal      [181.36, 31.65, 54.51, 1] ; #5F8A8B
            :sugar-plum      [325.07, 46.21, 56.86, 1] ; #914E75
            :twilight-lavender [328.62, 47.1, 54.12, 1] ; #8A496B
            :wintergreen-dream [166.8, 36.76, 53.33, 1] ; #56887D
            }))

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
