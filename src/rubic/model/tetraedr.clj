(ns rubic.model.tetraedr
  (:require [rubic.model.common :as common]))

(def tetraedr-faces-ids [:a :b :c :d])

(def connections [[[:a :b] [:a :c] [:a :d]]
                  [[:b :a] [:b :c] [:b :d]]
                  [[:c :a] [:c :b] [:c :d]]
                  [[:d :a] [:d :c] [:d :b]]])

(defn gen-tetraedr []
  (common/generate-solid tetraedr-faces-ids connections))


