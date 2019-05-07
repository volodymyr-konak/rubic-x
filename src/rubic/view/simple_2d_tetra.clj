(ns rubic.view.simple-2d-tetra
  (:require [quil.core :as quil]
            [rubic.view.utils :refer [domino-number]]))

(def id-display-mapping
  {:a 1
   :b 2
   :c 3
   :d 4})

(def id-color-mapping
  {:a '(250 250 250)
   :b '(200 0 0)
   :c '(0 200 0)
   :d '(0 0 200)})

#_(defn bit
    ([a] (bit a 0))
    ([a id]
     (quil/triangle 0 0 a 0 (* 1/2 a) (* 0.85 a))
     (domino-number id (* 1/2 a))))

(defn corner-bit [a id]
  (apply quil/fill (vec (id id-color-mapping)))
  (quil/with-translation
    [(* 1/2 a) (* (* 0.55 (quil/cos quil/THIRD-PI)) a)]
    ;[0 0]
    (quil/quad
      0 0
      (* (quil/cos quil/THIRD-PI) a) (* 0.85 a)
      (* (+ 1 (quil/cos quil/THIRD-PI)) a) (* 0.85 a)
      a 0)
    (domino-number (get id-display-mapping id) (* 1/2 a))))

(defn middle-line-bit [a id]
  (apply quil/fill (vec (id id-color-mapping)))
  (quil/with-translation
    ;[0 (* (/ 1 1.7) a)]
    [0 (* (* 0.55 (quil/cos quil/THIRD-PI)) a)]
    (quil/quad
      (* -0.5 a) 0
      (- a) (* a (quil/sin quil/THIRD-PI))
      a (* a (quil/sin quil/THIRD-PI))
      (* 0.5 a) 0)
    (domino-number (get id-display-mapping id) (* 1/2 a))))

(defn center-bit [a id]
  (apply quil/fill (vec (id id-color-mapping)))
  (quil/with-translation
    ;[0 (* (/ 1 1.7) a)]
    [0 (* (* 0.55 (quil/cos quil/THIRD-PI)) a)]
    (quil/triangle
      (* -0.5 a) 0
      0 (- (* a (quil/sin quil/THIRD-PI)))
      (* 0.5 a) 0)
    (domino-number (get id-display-mapping id) (* 1/2 a))))

(def spiral-triangular-face-map
  [[-1 -1] [0 -1] [1 -1] [0.5 0] [0 1] [-0.5 0] [0 -1/3]])


(defn render-triang-face [face-bits]
  (let [bit-size 80]
    (mapv (fn [id render-func rotate-angle]
            (quil/with-rotation
              [rotate-angle 0 0 1]
              (render-func bit-size id)))
          face-bits
          [corner-bit middle-line-bit
           corner-bit middle-line-bit
           corner-bit middle-line-bit
           center-bit]
          [0 0
           (* 2 quil/THIRD-PI) (* 2 quil/THIRD-PI)
           (* 4 quil/THIRD-PI) (* 4 quil/THIRD-PI) 0])))

(def faces-cube-map
  (let [scale 200]
    [{:rotation-vector    [quil/PI 1 0 0
                           (* 3 quil/THIRD-PI) 0 0 1]
      :translation-vector [0 0 (- scale)]}
     {:rotation-vector    [quil/THIRD-PI 1 0 0
                           (* 0 quil/THIRD-PI) 0 0 1]
      :translation-vector [0 0 (- scale)]}
     {:rotation-vector    [quil/THIRD-PI 1 0 0
                           (* 4 quil/THIRD-PI) 0 0 1]
      :translation-vector [0 0 (- scale)]}
     {:rotation-vector    [quil/THIRD-PI 1 0 0
                           (* 2 quil/THIRD-PI) 0 0 1]
      :translation-vector [0 0 (- scale)]}]))

(defn simple-plane [data]
  (let [tetr (-> data
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
                (render-triang-face (nth tetr i))))))
        faces-cube-map))))
