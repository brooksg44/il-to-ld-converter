(ns il-to-ld-converter.core
  (:require [il-to-ld-converter.parser :as parser]
            [il-to-ld-converter.converter :as converter]
            [clojure.pprint :as pprint])
  (:gen-class))

(defn convert-il-program
  "Convert an IL program to LD representation"
  [il-code]
  (let [parsed-il (parser/parse-il-program il-code)
        parsed-map (first parsed-il)] ; Simply take the first element of the LazySeq
    (println "Raw parsed-il:" parsed-il)
    (println "Parsed-map after unwrap:" parsed-map)
    (println "Instructions from parsed-map:" (:instructions parsed-map))
    (let [ld-program (converter/convert-il-to-ld parsed-map)]
      (println "Inside convert-il-program - Parsed IL:" parsed-il)
      (println "Inside convert-il-program - LD Program:" ld-program)
      ld-program)))

;; (defn -main
;;   "Main entry point for the IL to LD converter"
;;   [& args]
;;   (let [sample-il "LD %I0.0"]
;;     (println "Input IL Program:")
;;     (println sample-il)
;;     (println "\nParsed IL:")
;;     (let [parsed-il (parser/parse-il-program sample-il)]
;;       (pprint/pprint parsed-il))
;;     (println "\nLD Diagram:")
;;     (let [ld-program (convert-il-program sample-il)]
;;       (println "LD Program before generate:" ld-program)
;;       (println "Rungs:" (:rungs ld-program))
;;       (println (converter/generate-ld-diagram ld-program)))))

(defn -main
  [& args]
  (let [sample-il "
    LD %I0.0    ; Load input bit 0
    ANDN %I0.1  ; AND with inverted input bit 1
    ST %Q0.0    ; Store result to output bit 0
  "]
    (println "Input IL Program:")
    (println sample-il)
    (println "\nParsed IL:")
    (let [parsed-il (parser/parse-il-program sample-il)]
      (pprint/pprint parsed-il))
    (println "\nLD Diagram:")
    (let [ld-program (convert-il-program sample-il)]
      (println "LD Program before generate:" ld-program)
      (println "Rungs:" (:rungs ld-program))
      (println (converter/generate-ld-diagram ld-program)))))