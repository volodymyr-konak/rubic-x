(ns rubic.view.simple-2d-v2
  (:require [quil.core :as quil]))


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

(defn face [id]
  (let [bit-size 40]
    (doseq [i [-1 0 1]
            j [-1 0 1]]
      (quil/with-translation
        [(* i bit-size) (* j bit-size)]
        (bit bit-size id)))))

(defn simple-plane []
  (doseq [i (range 1 7)]
    (quil/with-translation
      [(+ 50 (* i 150)) 50]
      (face i))))
