(ns rubic.viewmodel.core
  (:require [quil.core :as quil]
            [rubic.model.core :as model]
            [rubic.view.core :as renderer]))

(defn model->view [model-data]
  [model-data])

(defn wrap-view-data [presentation-data-seq]
  {:selected-face      nil
   :rotated-face-angle nil
   :camera-angle       nil
   :solids             (for [solid presentation-data-seq]
                         {:id       0
                          :in-focus true
                          :layout   solid})})

(def model-state (atom (model/provide-model-snapshot)))

(def view-state (atom {}))

(defn model->view! []
  (reset! view-state
          (->> @model-state
               :solids
               (map model/solid->edn)
               wrap-view-data)))

(model->view!)

(defn reset-junk []
  (def model-state (atom (model/provide-model-snapshot)))
  (model->view!))

(defn temp-get-solid-from-model []
  (-> @model-state
      :solids
      first))

(defn revolve [face-kw n]
  (swap! model-state update-in [:solids] (fn [[g]] (vector (rubic.model.common/revolve-face g nil face-kw n))))
  (model->view!))

(defn setup []
  true)

(defn draw []
  (renderer/render view-state))

(quil/defsketch Rubic
                :title "Rubic"
                :setup setup
                :draw draw
                ;:key-pressed key-pressed
                ;:mouse-pressed mouse-pressed
                :frame-rate 30
                ;:renderer :p3d
                :size [1200 400])
