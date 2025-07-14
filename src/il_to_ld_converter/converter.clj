(ns il-to-ld-converter.converter
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str]
            [il-to-ld-converter.ld-visualizer :as viz]))

;; Convert IL operations to LD elements
(defn il-to-ld-element
  "Convert an IL instruction to an LD element"
  [instruction]
  (let [op (str (:operation instruction))] ; Convert symbol or string to string
    (match [op]
      ["LD"]   {:type :contact-load
                :operand (:operand instruction)
                :normally-closed? false}
      ["LDN"]  {:type :contact-load
                :operand (:operand instruction)
                :normally-closed? true}
      ["ST"]   {:type :coil-store
                :operand (:operand instruction)
                :normally-closed? false}
      ["STN"]  {:type :coil-store
                :operand (:operand instruction)
                :normally-closed? true}
      ["AND"]  {:type :contact-series
                :operand (:operand instruction)
                :normally-closed? false}
      ["ANDN"] {:type :contact-series
                :operand (:operand instruction)
                :normally-closed? true}
      ["OR"]   {:type :contact-parallel
                :operand (:operand instruction)
                :normally-closed? false}
      ["ORN"]  {:type :contact-parallel
                :operand (:operand instruction)
                :normally-closed? true}
      :else    {:type :unsupported-operation
                :operation op})))

(defn convert-il-to-ld
  "Convert an entire IL program to LD representation"
  [parsed-il]
  (println "Received parsed-il in convert-il-to-ld:" parsed-il)
  (let [instructions (:instructions parsed-il)]
    (println "Instructions to convert:" instructions)
    (if instructions
      (do
        (println "Converted rungs:" (mapv il-to-ld-element instructions))
        {:type :ld-program
         :rungs (mapv il-to-ld-element instructions)})
      (do
        (println "No instructions found, returning empty program")
        {:type :ld-program
         :rungs []}))))

(defn generate-ld-diagram
  "Generate a visual representation of the Ladder Diagram"
  [ld-program]
  (str (viz/generate-ascii-ld ld-program)
       "\n\n"
       (viz/generate-detailed-ld ld-program)))
