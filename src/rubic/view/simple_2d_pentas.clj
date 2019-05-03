(ns rubic.view.simple-2d-pentas
  (:require [quil.core :as quil]))


(def id-display-mapping
  {:a [1 :white]
   :b [2 :white]
   :c [3 :white]
   :d [4 :white]
   :e [5 :white]
   :f [6 :white]
   :g [1 :black]
   :h [2 :black]
   :k [3 :black]
   :l [4 :black]
   :m [5 :black]
   :n [6 :black]
   })

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
  ([a] (bit a [0 :white]))
  ([a [id color-kw]]
   (quil/fill ({:black 0 :white 100} color-kw))
   (quil/rect 0 0 a a 5)
   (quil/no-fill)
   (quil/stroke 200)
   (domino-number id a)
   (quil/no-stroke)
    ))

(def spiral-pentagon-face-map
  [[-2 -1] [0 -1] [2 -1]
           [2 -0]
   [2.5 2] [1.5 2]
          [0 2]
   [-1.5 2] [-2.5 2]
    [-2 0]
   [0 0]])

(defn render-pentagon-face [face-bits]
  (let [bit-size 30]
    (quil/stroke 0)
    (quil/text (str (last face-bits)) 0 0 )
    (quil/no-stroke)
    (mapv (fn [id [i j]]
            (quil/with-translation
              [(* i bit-size) (* j bit-size)]
              (bit bit-size (get id-display-mapping id))))
          face-bits
          spiral-pentagon-face-map)))

(defn simple-plane [data]
  (let [tetr (-> data
                 :solids
                 first
                 :layout)
        scale 40]
    (doseq [i (range 1 7)]
      (quil/with-translation
        [(- (* i (* 5 scale)) (* 2 scale)) scale]
        (render-pentagon-face (nth tetr (- i 1)))))
    (doseq [i (range 7 13)]
      (quil/with-translation
        [(- (* (- i 6) (* 5 scale)) (* 2 scale)) 400]
        (render-pentagon-face (nth tetr (- i 1)))))))
