(ns rubic.view.core
  (:require [rubic.view.simple-2d-v1 :as cube-2d]
            [rubic.view.simple-2d-tetra :as tetra-2d]
            [rubic.view.simple-2d-pentas :as penta-2d]))


(defn renderer-factory [figure-type dimensional-mode]
  (case figure-type
    :tetraedr (fn [data-atom] (tetra-2d/simple-plane @data-atom))
    :cube (fn [data-atom] (cube-2d/simple-plane @data-atom))
    :dodecahedron (fn [data-atom] (penta-2d/simple-plane @data-atom))))
