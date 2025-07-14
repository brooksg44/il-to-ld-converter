(ns il-to-ld-converter.ui
  (:require [cljfx.api :as fx]
            [il-to-ld-converter.parser :as parser]
            [il-to-ld-converter.converter :as converter]
            [il-to-ld-converter.ld-visualizer :as viz]
            [clojure.string :as str]))

;; Application state
(defonce *state
  (atom {:il-code ""
         :ld-output ""
         :status "Ready"
         :status-type :info
         :output-format :ascii}))

;; Event handlers
(defn handle-event [event]
  (println "Event received:" event))

(defn on-il-code-change [event]
  (swap! *state assoc :il-code event))

(defn on-convert-click [event]
  (let [il-code (:il-code @*state)
        output-format (:output-format @*state)]
    (try
      (if (str/blank? il-code)
        (swap! *state assoc
               :status "Please enter some IL code"
               :status-type :warning)
        (let [parsed-il (parser/parse-il-program il-code)
              parsed-map (first parsed-il)  ; Unwrap the LazySeq like core.clj does
              ld-program (converter/convert-il-to-ld parsed-map)
              ld-output (case output-format
                          :ascii (viz/generate-ascii-ld ld-program)
                          :detailed (viz/generate-detailed-ld ld-program)
                          :combined (converter/generate-ld-diagram ld-program))]
          (swap! *state assoc
                 :ld-output ld-output
                 :status "Conversion successful"
                 :status-type :success)))
      (catch Exception e
        (swap! *state assoc
               :ld-output ""
               :status (str "Error: " (.getMessage e))
               :status-type :error)))))

(defn on-clear-click [event]
  (swap! *state assoc
         :il-code ""
         :ld-output ""
         :status "Cleared"
         :status-type :info))

(defn on-load-example-click [event]
  (let [example-il "LD %I0.0    ; Load input bit 0\nANDN %I0.1  ; AND with inverted input bit 1\nST %Q0.0    ; Store result to output bit 0"]
    (swap! *state assoc
           :il-code example-il
           :status "Example loaded"
           :status-type :info)))

(defn on-output-format-change [event]
  (swap! *state assoc :output-format event))

(defn on-quit [event]
  (System/exit 0))

(defn on-show-about [event]
  (println "IL to LD Converter v0.1.0\nConverts IEC 61131-3 Instruction List to Ladder Diagram"))

;; UI Components
(defn status-style [status-type]
  (case status-type
    :success {:-fx-text-fill "green" :-fx-font-weight "bold"}
    :error {:-fx-text-fill "red" :-fx-font-weight "bold"}
    :warning {:-fx-text-fill "orange" :-fx-font-weight "bold"}
    :info {:-fx-text-fill "blue"}))

(defn root-view [{:keys [il-code ld-output status status-type output-format]}]
  {:fx/type :stage
   :showing true
   :title "IL to LD Converter - IEC 61131-3"
   :width 1000
   :height 700
   :on-close-request on-quit
   :scene {:fx/type :scene
           :root {:fx/type :border-pane
                  :top {:fx/type :menu-bar
                        :menus [{:fx/type :menu
                                 :text "File"
                                 :items [{:fx/type :menu-item
                                          :text "Load Example"
                                          :on-action on-load-example-click}
                                         {:fx/type :menu-item
                                          :text "Clear All"
                                          :on-action on-clear-click}
                                         {:fx/type :separator-menu-item}
                                         {:fx/type :menu-item
                                          :text "Exit"
                                          :on-action on-quit}]}
                                {:fx/type :menu
                                 :text "Help"
                                 :items [{:fx/type :menu-item
                                          :text "About"
                                          :on-action on-show-about}]}]}
                  :center {:fx/type :split-pane
                           :items [{:fx/type :v-box
                                    :children [{:fx/type :label
                                                :text "IL Code Input:"
                                                :style {:-fx-font-weight "bold"
                                                        :-fx-padding "5"}}
                                               {:fx/type :text-area
                                                :v-box/vgrow :always
                                                :text il-code
                                                :on-text-changed on-il-code-change
                                                :style {:-fx-font-family "monospace"
                                                        :-fx-font-size "12px"}}]}
                                   {:fx/type :v-box
                                    :children [{:fx/type :label
                                                :text "LD Diagram Output:"
                                                :style {:-fx-font-weight "bold"
                                                        :-fx-padding "5"}}
                                               {:fx/type :text-area
                                                :v-box/vgrow :always
                                                :text ld-output
                                                :editable false
                                                :style {:-fx-font-family "monospace"
                                                        :-fx-font-size "12px"
                                                        :-fx-background-color "#f5f5f5"}}]}]}
                  :bottom {:fx/type :v-box
                           :children [{:fx/type :h-box
                                       :alignment :center
                                       :spacing 10
                                       :padding 10
                                       :children [{:fx/type :label
                                                   :text "Output Format:"}
                                                  {:fx/type :choice-box
                                                   :value output-format
                                                   :items [:ascii :detailed :combined]
                                                   :on-value-changed on-output-format-change}
                                                  {:fx/type :region
                                                   :h-box/hgrow :always}
                                                  {:fx/type :button
                                                   :text "Convert IL to LD"
                                                   :on-action on-convert-click
                                                   :style {:-fx-font-size "14px"
                                                           :-fx-padding "10"}}
                                                  {:fx/type :button
                                                   :text "Clear All"
                                                   :on-action on-clear-click}]}
                                      {:fx/type :label
                                       :text status
                                       :style (merge {:-fx-padding "5"}
                                                     (status-style status-type))}]}}}})

;; App definition using simple renderer
(def app
  (fx/create-app *state
                 :desc-fn (fn [state]
                            (root-view state))))
                            
(defn start-app []
  (fx/mount-renderer *state #'app)
  (println "IL to LD Converter UI started"))

(defn stop-app []
  (fx/unmount-renderer *state #'app)
  (println "IL to LD Converter UI stopped"))
