(ns rubic.model.cube
  (:require [rubic.model.common :as common]))

(def cube-faces-ids [:a :b :c :d :e :f])
(def connections [[[:a :b] [:a :c] [:a :d] [:a :e]]
                  [[:f :b] [:f :c] [:f :d] [:f :e]]
                  [[:b :a] [:b :c] [:b :e] [:b :f]]
                  [[:c :a] [:c :b] [:c :d] [:c :f]]
                  [[:d :a] [:d :c] [:d :e] [:d :f]]
                  [[:e :a] [:e :b] [:e :d] [:e :f]]])

(defn gen-cube []
  (common/generate-solid cube-faces-ids connections))


