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
    [{:rotation-vector    [q/HALF-PI 0 0 1]
      :translation-vector [0 0 scale]}
     {:rotation-vector    [q/HALF-PI -1 0 0
                           q/PI 0 0 1]
      :translation-vector [0 0 scale]}
     {:rotation-vector    [q/HALF-PI -1 0 0
                           q/HALF-PI 0 0 1]
      :translation-vector [0 0 scale]}
     {:rotation-vector    [q/HALF-PI -1 0 0]
      :translation-vector [0 0 scale]}
     {:rotation-vector    [q/HALF-PI -1 0 0
                           q/HALF-PI 0 0 -1]
      :translation-vector [0 0 scale]}
     {:rotation-vector    [q/PI 0 1 0]
      :translation-vector [0 0 scale]}]))

(defn draw-cube [data]
  (let [cube (-> data
                 :solids
                 first
                 :layout)
        nested-rotation (fn [r-vec form]
                          (seq
                            ((reduce
                               (fn [form-executed rv]
                                 (fn [] (quil/with-rotation
                                          (vec rv)
                                          (form-executed))))
                               form
                               (partition 4 r-vec)))))]
    (seq
      (map-indexed
        (fn [i {:keys [rotation-vector translation-vector]}]
          (nested-rotation
            rotation-vector
            (fn []
              (quil/with-translation
                translation-vector
                (render-cube-face (nth cube i))))))
        faces-cube-map))))
