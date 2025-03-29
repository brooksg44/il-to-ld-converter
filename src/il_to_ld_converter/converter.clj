(ns il-to-ld-converter.converter
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str]))

;; Convert IL operations to LD elements
(defn il-to-ld-element
  "Convert an IL instruction to an LD element"
  [instruction]
  (match [(:operation instruction)]
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
              :operation (:operation instruction)}))

(defn convert-il-to-ld
  "Convert an entire IL program to LD representation"
  [parsed-il]
  (let [instructions (:instructions parsed-il)]
    {:type :ld-program
     :rungs (mapv il-to-ld-element instructions)}))

(defn generate-ld-diagram
  "Generate a visual representation of the Ladder Diagram"
  [ld-program]
  (let [rungs (:rungs ld-program)]
    (str "Ladder Diagram:\n"
         (str/join "\n"
                   (map-indexed
                    (fn [idx rung]
                      (format "Rung %d: %s (%s)"
                              (inc idx)
                              (:operand rung)
                              (:type rung)))
                    rungs)))))
