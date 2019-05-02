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
  (for [node (uber/nodes g)]
    (-> g
        (uber/out-edges node)
        ((partial mapv
                  (fn [x] (drop-last (uber/attr g x :bits)))))
        ((fn [x] (conj (apply concat x) node))))))

#_(defn solid->edn [g]
  "Outputs all bits in spiral for each face"
  ;FIXME here
  (map
    (fn [node] (uber/attr g node :bits-seq))
    (uber/nodes g)))

