(ns il-to-ld-converter.parser
  (:require [instaparse.core :as insta]
            [instaparse.transform :as insta-transform]
            [clojure.string :as str]))

;; IL Grammar using Instaparse
(def il-grammar
  "
  (* Whitespace and comment handling *)
  <ws> = #'\\s*'
  <comment> = #';.*'
  <eol> = #'\\n'
  
  program = line*
  <line> = ws (instruction ws comment? eol? | ws comment? eol? | eol)
  
  instruction = operation ws operand
  
  operation = 'LD' | 'LDN' | 'ST' | 'STN' | 'S' | 'R' | 'AND' | 'ANDN' | 
              'OR' | 'ORN' | 'XOR' | 'XORN' | 'ADD' | 'SUB' | 'MUL' | 
              'DIV' | 'GT' | 'GE' | 'EQ' | 'NE' | 'LE' | 'LT' | 'JMP'
  
  operand = identifier | numeric | memory_address
  
  identifier = #'[A-Za-z_][A-Za-z0-9_]*'
  numeric = #'[+-]?[0-9]+(\\.[0-9]+)?'
  memory_address = '%' ('I' | 'Q' | 'M') ('B' | 'W' | 'D' | 'L')? #'[0-9]+(\\.[0-9]+)?'
  ")

;; Create the parser
(def parse-il
  (insta/parser il-grammar))

;; Parse an IL program 
(defn parse
  "Parse an Instruction List program"
  [il-code]
  (let [parsed (parse-il il-code)]
    (if (insta/failure? parsed)
      (throw (ex-info "Parsing failed"
                      {:error (insta/get-failure parsed)}))
      parsed)))

;; Transform parsed IL to a more usable structure
(defn transform-il [parsed]
  (insta-transform/transform
   {:program (fn [& instructions]
               {:type :program
                :instructions (vec instructions)})
    :instruction (fn [& args]
                   (let [label (first (filter #(= (:type %) :label) args))
                         operation (first (filter #(= (:type %) :operation) args))
                         operand (first (filter #(= (:type %) :operand) args))]
                     {:type :instruction
                      :label (when label (:value label))
                      :operation (:value operation)
                      :operand (when operand (:value operand))}))
    :label (fn [label]
             {:type :label
              :value (when (seq label) (str/replace label #":" ""))})
    :operation (fn [op]
                 {:type :operation
                  :value op})
    :operand (fn [operand]
               {:type :operand
                :value operand})
    :identifier identity
    :numeric #(Float/parseFloat %)
    :memory_address identity} parsed))


;; High-level parsing function
(defn parse-il-program
  "Parse an IL program and transform it"
  [il-code]
  (let [parsed (parse il-code)]
    (transform-il parsed)))


(comment
  ;; Test with a simpler example first
  (let [simple-il "LD %I0.0"]
    (insta/parses parse-il simple-il :trace true)


    ;; Parse the simple IL code
    (parse-il-program simple-il))

  ;; Then test with the full example
  (let [sample-il "
      LD %I0.0    ; Load input bit 0
      ANDN %I0.1  ; AND with inverted input bit 1
      ST %Q0.0    ; Store result to output bit 0
    "]
    (insta/parses parse-il sample-il :trace true)

    (parse-il-program sample-il))
  :rcf)