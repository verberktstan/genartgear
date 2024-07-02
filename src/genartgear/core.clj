(ns genartgear.core
  "Utilities for sketching genart stuff with quil and genartlib. Most notably the sketch macro."
  {:added "0.0.1-SNAPSHOT"
   :author "Stan Verberkt"}
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as str]
            [genartlib.util :as u]
            [quil.core :as q]
            [quil.middleware :as m]))

(defn- setup
  "Returns a basic setup function, returns props for use with quil's fun-mode"
  ([] (setup nil))
  ([props]
   (fn []
     (u/set-color-mode)
     (q/no-loop)
     (or props {}))))

(defn- dir
  "Returns the current directory (unix). Intended to be used with git-hash below."
  []
  (-> "pwd" sh :out str/trim-newline))

(defn- git-hash
  "Returns the short hash for the most recent git commit, if any."
  [directory]
  (let [s (-> (sh "git" "rev-parse" "--short" "HEAD" :dir (or directory (dir)))
              :out
              str/trim-newline)]
    (when-not (str/blank? s) s)))

(defn wrap-draw [draw-fn]
  (fn [{:keys  [save seeds title]
        ::keys [directory]
        :as    props}]
    (when seeds (assert (vector? seeds)))
    (doseq [img-num (range (or (some-> (when save seeds) count) ; When save and seeds are given, produce 1 img for each seed
                               (when (pos-int? save) save) ; When save is a pos-int, produce n images
                               1))]
      (newline)
      (println "generating image" img-num)
      (let [seed      (or (when seeds (nth seeds img-num))
                          (System/nanoTime))
            file-name (str (or title "sketch")
                           (when-let [gh (and directory
                                              (git-hash directory))]
                             (str "-hash-" gh))
                           "-time-" (System/currentTimeMillis)
                           "-seed-" seed
                           ".tif")]
        (println "Setting noise & random seed to:" seed)
        (q/noise-seed seed) ; TODO: Set noise and random seed to different seeds
        (q/random-seed seed)

        (draw-fn props) ; Call the draw function with the supplied props

        (when save
          (q/save file-name)
          (println "done saving" file-name))))))

(defmacro sketch
  "Defines a Quil sketch with the supplied draw function."
  [draw & props]
  (let [{:keys [title] :or {title "genartgear-sketch"} :as m} (apply hash-map props)]
    (concat
      `(q/defsketch
         ~(symbol title)
         :title ~title
         :setup ~(-> m (assoc ::directory (dir)) setup)
         :draw (wrap-draw ~draw)
         :size ~(or (:size m) [500 300])
         :features [:keep-on-top]
         :middleware [m/fun-mode])
      (reduce into (dissoc m :title :setup :draw :size :features :middleware)))))
