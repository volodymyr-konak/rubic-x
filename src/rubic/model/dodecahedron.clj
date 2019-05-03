(ns rubic.model.dodecahedron
  (:require [rubic.model.common :as common]))


(def dodeca-faces-ids [:a
                       :b :c :d :e :f
                       :g :h :k :l :m
                       :n])

(def connections-per-node
  {:a [:f :e :d :c :b]
   :b [:a :c :g :m :f]
   :c [:a :d :h :g :b]
   :d [:c :a :e :k :h]
   :e [:d :a :f :l :k]
   :f [:e :a :b :m :l]

   :g [:b :c :h :n :m]
   :h [:c :d :k :n :g]
   :k [:h :d :e :l :n]
   :l [:k :e :f :m :n]
   :m [:l :f :b :g :n]
   :n [:g :h :k :l :m]})

(def connections (for [[node destintations] connections-per-node]
                   (for [dest destintations]
                     [node dest])))

(defn gen-dodeca []
  (common/generate-solid dodeca-faces-ids connections))
