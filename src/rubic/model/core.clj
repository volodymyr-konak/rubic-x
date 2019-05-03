(ns rubic.model.core
  (:require [ubergraph.core :as uber]
            [rubic.model.cube :as cube]
            [rubic.model.tetraedr :as tetra]
            [rubic.model.dodecahedron :as dodeca]))

(defn create-solid [type]
  (case type
    :tetraedr (tetra/gen-tetraedr)
    :cube (cube/gen-cube)
    :dodecahedron (dodeca/gen-dodeca)))

(defn provide-model-snapshot [figure-type dimensions-mode]
  ""
  {:dimensions-mode dimensions-mode
   :solids [(create-solid figure-type)]})

(defn solid->edn [g]
  "Outputs all bits in spiral for each face"
  ;FIXME here
  (map
    (fn [node] (conj (uber/attr g node :bits-seq) node))
    (uber/nodes g)))

