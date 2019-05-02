(defproject RubicX "version"
  :description "Rubic's Platonic Figures in 3 or 4 dimensions"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [quil "3.0.0" :exclusions [org.clojure/clojure]]
                 [ubergraph "0.5.2"]]
  :main rubic.core)
