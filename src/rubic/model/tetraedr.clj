(ns rubic.model.tetraedr
  (:require [ubergraph.core :as uber]
            [rubic.model.common :as common]))

(def tetraedr-faces-ids [:a :b :c :d])

(defn gen-tetra []
  (-> (uber/ubergraph
        true false)
      (uber/add-directed-edges*
        (for [face-id tetraedr-faces-ids
              another-f-id tetraedr-faces-ids
              :when (not= face-id another-f-id)]
          [face-id
           another-f-id
           {:bits [face-id face-id face-id]}]))
      ))

