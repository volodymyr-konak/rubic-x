(ns rubic.model.common
  (:require [ubergraph.core :as uber]))

(defn rotate-seq [s d] (concat (drop d s) (take d s)))

(defn rotate-hash-map-vals [m shift]
  (map (fn [foo bar] [foo bar]) (keys m) (rotate-seq (vals m) shift)))


(defn replace-bits-subseq [iseq replacement position]
  "Replaces subsequence of the given sequence from desired position.
  Keeps the length of sequence without change.
  Overlaps not-fitting values to the begining."
  (let [[before after] (split-at position iseq)
        [_ ending] (split-at (count replacement) after)
        raw-result (concat before replacement ending)]
    (if (< (count replacement) (count after))
      raw-result
      (apply replace-bits-subseq
             (conj
               (split-at (count iseq) raw-result)
               0)))))


(defn shift-lines-on-target [graph face-node-id shift-value]
  (let []
    (uber/add-attrs
      graph face-node-id
      {:bits-seq (vec (rotate-seq
                        (uber/attr graph face-node-id :bits-seq)
                        (* 2 shift-value)))})))

(defn shift-lines-around-target [graph face-node-id shift-value]
  (let [g-edges (sort-by
                  (fn [e1]
                    (uber/attr graph (:dest e1) (:src e1) :order))
                  (uber/in-edges graph face-node-id))
        edges-bits (mapv (fn [e] ((uber/attr graph e :bits-fn) graph)) g-edges)]
    (reduce
      (fn [g [edge bits]]
        (let [edge-id-for-node (uber/attr g edge :order)
              replace-index (* 2 edge-id-for-node)
              bits-seq (uber/attr g (:src edge) :bits-seq)
              processed-bits-seq (replace-bits-subseq bits-seq bits replace-index)]
          (uber/add-attrs g (:src edge)
                          {:bits-seq (vec processed-bits-seq)})))
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
          (shift-lines-on-target face-id direction)))))


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
                                     bits-seq (uber/attr graph-state src :bits-seq)]
                                 (subvec
                                   (conj bits-seq (first bits-seq))
                                   (dec line-base-bit-index)
                                   (-> line-base-bit-index
                                       inc
                                       inc))))}])
                face-connections)))))))
