(ns rubic.model.common
  (:require [ubergraph.core :as uber]))

(defn rotate-seq [s d] (concat (drop d s) (take d s)))

(defn rotate-hash-map-vals [m shift]
  (map (fn [foo bar] [foo bar]) (keys m) (rotate-seq (vals m) shift)))

(defn shift-lines-on-target [graph face-node-id shift-value]
  (let [g-edges (uber/out-edges graph face-node-id)
        edges-bits (map (fn [e] (uber/attr graph e :bits)) g-edges)]
    (reduce
      (fn [g [to-face bits]] (uber/add-attrs g to-face {:bits bits}))
      graph
      (rotate-hash-map-vals (zipmap g-edges edges-bits) shift-value))))

#_(defn shift-lines-around-target [graph face-node-id shift-value]
    (let [g-edges (uber/in-edges graph face-node-id)
          edges-bits (mapv (fn [e] (uber/attr graph e :bits)) g-edges)]
      (reduce
        (fn [g [from-face bits]]
          (-> g
              ;(uber/set-attrs from-face {:bits bits})
              (uber/out-edges (:src from-face))))
        graph
        (rotate-hash-map-vals (zipmap g-edges edges-bits) shift-value))))

(defn shift-lines-around-target [graph face-node-id shift-value]
  (let [g-edges (uber/in-edges graph face-node-id)
        edges-bits (mapv (fn [e] (uber/attr graph e :bits)) g-edges)]
    (reduce
      (fn [g [from-face bits]] (uber/add-attrs g from-face {:bits bits}))
      graph
      (rotate-hash-map-vals (zipmap g-edges edges-bits) shift-value))))

(defn revolve-face [g1 g2 face-id direction]
  "Invokes changes in every face, connected to target one.
  Reassigns every incoming-edge(ie) value for target face with a shift.
  Changes every neighbour edge of every ie"
  (when (nil? g2)
    (let []
      (-> g1
          (shift-lines-around-target face-id direction)
          (shift-lines-on-target face-id direction)
          )
      ;(clojure.pprint/pprint g1)
      )))


(defn generate-solid [faces-ids connections-per-face]
  (let [solid-graph (uber/ubergraph
                      true false)]
    (-> solid-graph
        (uber/add-nodes-with-attrs*
          (for [node-id faces-ids]
            [node-id
             ; FIXME this is wrong (+ 2 ...
             {:bits-seq (vec (repeat (+ 2 (count faces-ids)) node-id))}]))
        (uber/add-directed-edges*
          (apply
            concat
            (for [face-connections connections-per-face]
              (map-indexed
                (fn [i [src dst]]
                  [src
                   dst
                   {:order   i
                    :bits    [src src src]
                    :bits-fn (fn [graph-state]
                               "Returns a line of 3 bits, on the edge between src and dst"
                               (let [line-base-bit-index (dec (* 2 (inc i)))
                                     bits-seq (uber/attr graph-state src :bits-seq)
                                     _ (clojure.pprint/pprint [(conj bits-seq (first bits-seq))
                                                               (dec line-base-bit-index)
                                                               (-> line-base-bit-index
                                                                   inc
                                                                   inc)])]
                                 (subvec
                                   (conj bits-seq (first bits-seq))
                                   (dec line-base-bit-index)
                                   (-> line-base-bit-index
                                       inc
                                       inc))))}])
                face-connections)))))))
