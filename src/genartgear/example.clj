(ns genartgear.example
  (:require [genartgear.core :as gg]
            [quil.core :as q]
            [quil.middleware :as m]))

(defn- draw [{::keys [hue] :as props}]
  (println props)
  (q/background 255)
  (q/with-fill [hue 89 55]
    (q/ellipse 100 100 200 200)))

                                        ; Copy this defsketch into your ns to use it:w

(q/defsketch example
  :title "Example"
  :setup (gg/setup {:directory (gg/dir) ; Supply this to parse the short git hash and use it in the filename
                    :title     "example" ; Optional: Supply a title for use in the filename

                    ::hue 180 ; Just some props that are passed on into the draw function

                    #_#_:save 3 ; Save 3 images

                    #_#_:save false ; Don't save

                    #_#_:seeds [123 234]
                    #_#_:save  true ; Save 2 images with the above seeds
                    })
  :draw (gg/wrap-draw draw)
  :size [1300 800]
  :features [:keep-on-top]
  :middleware [m/fun-mode])

;; The most basic example that ever existed.
#_#_(defn- draw [_]
      (q/background (q/random 255)))

(q/defsketch basic-example
  :title "basic-example"
  :setup (gg/setup)
  :draw (gg/wrap-draw draw)
  :size [800 500]
  :features [:keep-on-top]
  :middleware [m/fun-mode])
