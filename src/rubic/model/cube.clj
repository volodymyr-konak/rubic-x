(ns rubic.model.cube
  (:require [rubic.model.common :as common]))

(def cube-faces-ids [:a
                     :b :c :d :e
                     :f])

(def connections-per-node
  {:a [:e :d :c :b]
   :b [:a :c :f :e]
   :c [:a :d :f :b]
   :d [:c :a :e :f]
   :e [:d :a :b :f]
   :f [:b :c :d :e]})

(def connections (for [[node destintations] connections-per-node]
                   (for [dest destintations]
                     [node dest])))

(defn gen-cube []
  (common/generate-solid cube-faces-ids connections))


