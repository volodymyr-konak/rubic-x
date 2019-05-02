(ns rubic.model.core
  (:require [ubergraph.core :as uber]
            [rubic.model.cube :as cube]
            [rubic.model.tetraedr :as tetra]))

(defn create-solid []
  (cube/gen-cube))

(defn provide-model-snapshot []
  ""
  {:multi-dimensional-graph nil
   :solids [(create-solid)]})

(defn solid->edn [g]
  "Outputs all bits in spiral for each face"
  ;FIXME here
  (map
    (fn [node] (conj (uber/attr g node :bits-seq) node))
    (uber/nodes g)))

