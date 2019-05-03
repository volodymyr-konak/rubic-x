(ns rubic.view.cube-3d
  (:require [quil.core :as quil]
            [rubic.view.utils :refer [domino-number]]
            [quil.core :as q]))

(def id-display-mapping
  {:a 1
   :b 2
   :c 3
   :d 4
   :e 5
   :f 6})

(defn bit
  ([a] (bit a 0))
  ([a id]
   (quil/rect 0 0 a a 5)
   (domino-number id a)))

(def spiral-square-face-map
  [[-1 -1] [0 -1] [1 -1] [1 0] [1 1] [0 1] [-1 1] [-1 0] [0 0]])

(defn render-cube-face [face-bits]
  (let [bit-size 40]
    (mapv (fn [id [i j]]
            (quil/with-translation
              [(* i bit-size) (* j bit-size)]
              (bit bit-size (get id-display-mapping id))))
          face-bits
          spiral-square-face-map)))

(def faces-cube-map
  (let [scale 200]
    [{:rotation    [0 0 0 0]
     :translation [0 0 scale]}
     {:rotation    [(* 3 q/HALF-PI) 1 0 0]
      :translation [0 0 (- scale)]}
    {:rotation    [(* 2 q/THIRD-PI) -1 -1 1]
     :translation [0 0 scale]}
     {:rotation    [(* 4 q/THIRD-PI) 1 1 1]
      :translation [0 0 scale]}
     {:rotation    [(* 3 q/HALF-PI) 0 1 0]
      :translation [0 0 (- scale)]}
     {:rotation    [0 0 0 0]
      :translation [0 0 (- scale)]}]))

(defn draw-cube [data]
  (let [cube (-> data
                 :solids
                 first
                 :layout)]
    (seq
      (map-indexed
       (fn [i f]

         (quil/with-rotation
           (:rotation f)
           (quil/with-translation
             (:translation f)
             (render-cube-face (nth cube i)))))
       faces-cube-map))
    #_(doseq [f faces-cube-map]
      (quil/with-translation
        (:translation f)
        ;[(+ 50 (* i 150)) 50]
        (render-cube-face (nth cube (- i 1)))))))
