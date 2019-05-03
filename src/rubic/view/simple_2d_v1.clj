(ns rubic.view.simple-2d-v1
  (:require [quil.core :as quil]))

(def id-display-mapping
  {:a 1
   :b 2
   :c 3
   :d 4
   :e 5
   :f 6})

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
    (quil/text "?" (/ a 2) (/ a 2))))

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

(defn simple-plane [data]
  (let [cube (-> data
                 :solids
                 first
                 :layout)]
    (doseq [i (range 1 7)]
      (quil/with-translation
        [(+ 50 (* i 150)) 50]
        (render-cube-face (nth cube (- i 1)))))))
