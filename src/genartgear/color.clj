(ns genartgear.color
  "Color palettes and modifing based on HSBA colors."
  {:added "0.1.3-SNAPSHOT"
   :author "Stan Verberkt"}
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
            }
   #:fragrance{:baby-powder [60, 1.57, 99.61, 1] ; #FEFEFA
               :banana      [47.04, 83.53, 100, 1] ; #FFD12A
               :blueberry   [220.36, 68.02, 96.86, 1] ; #4F86F7
               :bubble-gum  [309.55, 17.25, 100, 1] ; #FFD3F8
               :cedar-chest [7.97, 63.68, 78.82, 1] ; #C95A49
               :cherry      [349, 82.57, 85.49, 1] ; #DA2647
               :chocolate   [21.94, 49.21, 74.12, 1] ; #BD8260
               :coconut     [0, 0, 99.61, 1] ; #FEFEFE
               :daffodil    [60, 80.78, 100, 1] ; #FFFF31
               :dirt        [29.17, 46.45, 60.78, 1] ; 9B7653
               :eucalyptus  [160.82, 68.37, 84.31, 1] ; #44D7A8
               :fresh-air   [196.18, 34.9, 100, 1] ; #A6E7FF
               :grape       [272.2, 73.21, 65.88, 1] ; #6F2DA8
               :jelly-bean  [8.14, 64.22, 85.49, 1] ; #DA614E
               :leather-jacket [135, 30.19, 20.78, 1] ; #253529
               :lemon       [60, 78.04, 100, 1] ; #FFFF38
               :licorice    [6, 38.46 10.2, 1] ; #1A1110
               :lilac       [287.23, 39.33, 93.73 1] ; #DB91EF
               :lime        [76.18, 99.18 95.29, 1]  ; #B2F302
               :lumber      [27.6, 19.61, 100, 1] ; #FFE4CD
               :new-car     [223.27, 83.33, 77.65, 1] ; #214FC6
               :orange      [13.33, 60, 100, 1] ; #FF8866
               :peach       [19.71, 27.45, 100, 1] ; #FFD0B9
               :pine        [156.13, 57.41, 63.53, 1] ; #45A27D
               :rose        [0, 68.63, 100, 1] ; #FF5050
               :shampoo     [317.5, 18.82, 100, 1] ; #FFCFF1
               :smoke       [132, 11.54, 50.98, 1] ; #738276
               :soap        [249.23, 16.32, 93.73, 1] ; #CEC8EF
               :strawberry  [341.11, 64.29, 98.82, 1] ; #FC5A8D
               :tulip       [357, 47.06, 100, 1] ; #FF878D
               }
   #:jewelry-stones{:amethyst [244.14, 37.66, 60.39, 1] ; #64609A
                    :citrine  [20, 93.88, 57.65, 1] ; #933709
                    :emerald  [167.11, 88.17, 66.27, 1] ; #14A989
                    :jade     [164.29, 54.55, 60.39, 1] ; #469A84
                    :jasper   [7.92, 69.23, 81.57, 1] ; #D05340
                    :lapis-lazuli [219.15, 63.78, 72.55, 1] ; #436CB9
                    :malachite [181.5, 53.33, 58.82, 1] ; #469496
                    :moonstone [191.11, 69.95, 75.69, 1] ; #3AA8C1
                    :onyx     [195, 7.02, 22.35, 1] ; #353839
                    :peridot  [61.19, 58.38, 67.84, 1] ; #ABAD48
                    :pink-pearl [345, 36.36, 69.02, 1] ; #B07080
                    :rose-quartz [319.04, 55.03, 74.12, 1] ; #BD559C
                    :ruby     [336.79, 62.35, 66.67, 1] ; #AA4069
                    :sapphire [215.17, 72.05, 63.14, 1] ; #2D5DA1
                    :smokey-topaz [14.75, 90.08, 51.37, 1] ; #832A0D
                    :tigers-eye [31.14, 87.29, 70.98, 1] ; #B56917
                    }
   #:magic-scent{:burnt-sienna   [13.82, 65.24, 91.37, 1] ; #E97451
                 :tickle-me-pink [342.1, 49.21, 98.82, 1] ; #FC80A5
                 :brick-red      [351.76 77.27 77.65 1] ; #C62D42
                 :wisteria       [281, 27.27, 86.27, 1] ; #C9A0DC
                 :sky-blue       [189.83, 49.57, 91.76, 1] ; #76D7EA
                 :orange         [25, 80, 100, 1] ; #FF8833
                 :jungle-green   [163.38, 76.02, 67.06, 1] ; #29AB87
                 :brown          [14.34, 64.57, 68.63, 1] ; #AF593E
                 :pine-green     [175.46, 99.17, 47.06, 1] ; #01786F
                 :peach          [25.71, 35.69, 100, 1] ; #FFCBA4
                 :goldenrod      [44.7, 59.13, 98.82, 1] ; #FCD667
                 :red            [345.99, 95.78, 92.94, 1] ; #ED0A3F
                 :yellow         [51.8, 55.38, 98.43, 1] ; #FBE870
                 :dandelion      [45.84, 63.39, 99.61, 1] ; #FED85D
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
