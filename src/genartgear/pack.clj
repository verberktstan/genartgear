(ns genartgear.pack
  "Pack objects based on simplistic collision detection."
  {:added "0.1.5"
   :author "Stan Verberkt"}
  (:require [genartgear.core :refer [sketch]]
            [genartlib.algebra :as a]
            [genartlib.util :as u]
            [quil.core :as q]))

(def x first)
(def y second)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Private functions, used as default for pack

(defn- random-point []
  (let [rx (q/random 1)
        ry (q/random 1)]
    {:rpoint [rx ry]
     :point  [(u/w rx) (u/h ry)]}))

(defn- margin-fn [{:keys [rpoint]} _]
  (u/w
    (or
      (some->> rpoint y (q/lerp 1/40 1/400))
      1/220)))

(defn- collision?
  [point-a]
  (fn collision* [point-b]
    (let [margin (margin-fn point-a point-b)]
      (< (apply a/point-dist (map :point [point-a point-b])) margin))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; The packing transducer

(defn- pack*
  "A stateful transducer that returns packed items.
  :remove-fn should accept an item and return a function that, when supplied another
  item, returns a truethy value in case the item is NOT to be included in the result.
  :max-retries & :init-state are self-explanatory, right?"
  ([] (pack* nil))
  ([{:keys [max-retries remove-fn init-state]
     :or   {max-retries 10
            remove-fn   collision?}}]
   (-> max-retries pos-int? assert)
   (fn pack** [xf]
     (let [state        (volatile! init-state)
           retries-left (volatile! max-retries)]
       (fn pack***
         ([] (xf))
         ([result] (xf result))
         ([result item]
          (cond
            (zero? @retries-left)
            (reduced result)

            (some (remove-fn item) #_result @state)
            (do (vswap! retries-left dec)
                result)

            :else
            (do (vreset! retries-left max-retries)
                (vswap! state conj item)
                (xf result item)))))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Public pack function

(defn pack
  {:added "0.1.5"
   :arglist '([{:keys [make-item n init-state max-retries remove-fn]}])
   :doc "Makes items with the :make-item function, and packs those.
   :make-item (fn) should not accept any arguments and return a new/different item
   on every call. pack returns up to :n (positive integer value) items packed.
   pack also accepts :remove-fn, :max-retries & :init-state, see pack* docstring
   for more info.
   Make your own magic by implementing :make-item and :remove-fn. Tweak :n and
   :max-retries for optimization"}
  [{:keys [make-item n]
    :or   {make-item random-point n 999}
    :as   props}]
  (-> n pos-int? assert)
  (let [print-count (comp #(println "Packed" % "items.") count)]
    (-> (transduce (comp (pack* props) (take n)) conj (repeatedly make-item))
        (doto print-count))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Example draw fn and sketch

(comment
  (defn- draw
    [_]
    (q/background 360)

    (-> 1/150 u/w q/stroke-weight)

    ;; Call pack with :max-retries and :n props
    (doseq [{:keys [point]} (-> {:max-retries 9999 :n 4000} pack time)]
      (q/with-translation point
        (q/point 0 0))))

  (sketch
    draw
    :title "Pack example"
    :size [1230 760])
  )



