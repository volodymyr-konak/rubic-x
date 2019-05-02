(ns rubic.view.core
  (:require [rubic.view.simple-2d-v1 :as cube-3d]))


(defn render [data]
  (cube-3d/simple-plane @data))
