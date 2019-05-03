(ns rubic.view.simple-2d-tetra
  (:require [quil.core :as quil]))


(def id-display-mapping
  {:a 1
   :b 2
   :c 3
   :d 4})

(defn domino-number [number a]
  (case number
    0 false
    1 (quil/ellipse (/ a 2) (/ a 2) 4 4)
    2 (doall
        [(quil/ellipse (* a 1/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 3/4) (* a 3/4) 4 4)])
    3 (doall
        [(quil/ellipse (* a 1/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 1/2) (* a 1/2) 4 4)
         (quil/ellipse (* a 3/4) (* a 3/4) 4 4)])
    4 (doall
        [(quil/ellipse (* a 1/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 1/4) (* a 3/4) 4 4)
         (quil/ellipse (* a 3/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 3/4) (* a 3/4) 4 4)])
    5 (doall
        [(quil/ellipse (* a 1/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 1/4) (* a 3/4) 4 4)
         (quil/ellipse (* a 1/2) (* a 1/2) 4 4)
         (quil/ellipse (* a 3/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 3/4) (* a 3/4) 4 4)])
    6 (doall
        [(quil/ellipse (* a 1/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 1/4) (* a 3/4) 4 4)
         (quil/ellipse (* a 1/4) (* a 1/2) 4 4)
         (quil/ellipse (* a 3/4) (* a 1/2) 4 4)
         (quil/ellipse (* a 3/4) (* a 1/4) 4 4)
         (quil/ellipse (* a 3/4) (* a 3/4) 4 4)])
    :else (quil/text "?" (/ a 2) (/ a 2))))

(defn bit
  ([a] (bit a 0))
  ([a id]
   (quil/rect 0 0 a a 5)
   (domino-number id a)))

(def spiral-triangular-face-map
  [[-1.5 -1] [0 -1] [1.5 -1] [1 0] [0 1] [-1 0] [0 0]])

(defn render-triang-face [face-bits]
  (let [bit-size 40]
    (mapv (fn [id [i j]]
            (quil/with-translation
              [(* i bit-size) (* j bit-size)]
              (bit bit-size (get id-display-mapping id))))
          face-bits
          spiral-triangular-face-map)))

(defn simple-plane [data]
  (let [tetr (-> data
                 :solids
                 first
                 :layout)]
    (doseq [i (range 1 5)]
      (quil/with-translation
        [(+ 50 (* i 150)) 50]
        (render-triang-face (nth tetr (- i 1)))))))