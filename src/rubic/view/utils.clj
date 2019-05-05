(ns rubic.view.utils
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
    (quil/text "?" (/ a 2) (/ a 2))))
