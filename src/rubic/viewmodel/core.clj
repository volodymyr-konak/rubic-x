(ns rubic.viewmodel.core
  (:require [quil.core :as quil]
            [rubic.model.core :as model]
            [rubic.view.core :as viz]
            [rubic.view.input-processor :as input]))

(def simple-config [:cube :3d])
(def simple-config [:tetraedr :3d])
;(def simple-config [:dodecahedron :3d])

(def renderer (apply viz/renderer-factory simple-config))

(defn wrap-view-data [presentation-data-seq]
  {:selected-face      nil
   :rotated-face-angle nil
   :camera-angle       nil
   :solids             (for [solid presentation-data-seq]
                         {:id       0
                          :in-focus true
                          :layout   solid})})

(def model-state (atom (apply model/provide-model-snapshot simple-config)))

(def view-state (atom {}))

(defn model->view! []
  (reset! view-state
          (->> @model-state
               :solids
               (map model/solid->edn)
               wrap-view-data)))

(model->view!)

(defn reset-junk []
  (def model-state (atom (model/provide-model-snapshot :cube :3d)))
  (model->view!))

(defn temp-get-solid-from-model []
  (-> @model-state
      :solids
      first))

(defn revolve [face-kw n]
  (swap! model-state update-in [:solids]
         (fn [[g]]
           (vector
             (rubic.model.common/revolve-face g nil face-kw n))))
  (model->view!))

(defn setup []
  true)

(defn draw []
  "looped executed func"
  (quil/background 200)
  (let [last-key (input/read-from-input-queue!)]
    ; FIXME unhardcode to dynamic module
    (when (contains? #{:a :b :c :d :e :f :g :h :k :l :m :n} last-key)
      (revolve last-key 1))

    (quil/camera 500 300 -200 0 0 0 0 0 -1)
    (renderer view-state)))

(quil/defsketch Rubic
                :title "Rubic"
                :setup setup
                :draw draw
                :key-pressed input/keyboard-key-pressed
                ;:mouse-pressed mouse-pressed
                :frame-rate 30
                :renderer :p3d
                :size [1200 800])
