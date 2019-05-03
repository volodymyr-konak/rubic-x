(ns rubic.view.input-processor
  (:require [quil.core :as quil]))

(def input-state (atom {:queue []}))

(defn pop-from-input-queue! []
  (swap! input-state update :queue (fn [q] (rest q))))

(defn read-from-input-queue! []
  (let [out (-> @input-state :queue first)]
    (pop-from-input-queue!)
    out))

(defn keyboard-key-pressed []
  (when (quil/key-pressed?)
    (swap! input-state update :queue (fn [q] (conj q (quil/key-as-keyword))))
    (clojure.pprint/pprint (quil/key-as-keyword))))