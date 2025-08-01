<<<<<<< HEAD
(ns il-to-ld-converter.parser
  (:require [instaparse.core :as insta]
            [instaparse.transform :as insta-transform]
            [clojure.string :as str]
            [clojure.pprint :as pp]))

;; IL Grammar using Instaparse - ultra simplified approach
(def il-grammar
  "
  <S> = program
  program = (statement | <nl> | <ws>)*
  statement = (labeled-instr | instruction | comment)
  
  instruction = operation <ws-plus> operand (<ws> comment)?
  labeled-instr = label <ws-opt> operation <ws-plus> operand (<ws> comment)?
  
  label = identifier ':'
  operation = 'LD' | 'LDN' | 'ST' | 'STN' | 'S' | 'R' | 'AND' | 'ANDN' | 
              'OR' | 'ORN' | 'XOR' | 'XORN' | 'ADD' | 'SUB' | 'MUL' | 
              'DIV' | 'GT' | 'GE' | 'EQ' | 'NE' | 'LE' | 'LT' | 'JMP'
  
  operand = memory-address | identifier | numeric
  
  identifier = #'[a-zA-Z_][a-zA-Z0-9_]*'
  numeric = #'[+-]?[0-9]+(\\.[0-9]+)?'
  memory-address = #'%[IQM][XBWDL]?[0-9]+(\\.[0-9]+)?'
  
  comment = #';.*'
  <ws> = #'[ \\t]'
  <ws-opt> = #'[ \\t]*'
  <ws-plus> = #'[ \\t]+'
  <nl> = #'\\r?\\n'
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
                      {:error (insta/get-failure parsed)
                       :input il-code}))
      parsed)))

;; Transform parsed IL to a more usable structure
(defn transform-il [parsed]
  (insta-transform/transform
   {:program (fn [& statements]
               {:type :program
                :instructions (vec (remove nil? statements))})
    :statement identity
    :instruction (fn [op operand & rest]
                   {:type :instruction
                    :operation op
                    :operand operand})
    :labeled-instr (fn [label op operand & rest]
                     {:type :instruction
                      :label label
                      :operation op
                      :operand operand})
    :label (fn [id _] id) ; Remove the colon
    :operation identity
    :operand identity
    :identifier identity
    :numeric #(try (Float/parseFloat %) (catch Exception _ %))
    :memory-address identity
    :comment (fn [_] nil)}
   parsed))

;; High-level parsing function
(defn parse-il-program
  "Parse an IL program and transform it"
  [il-code]
  (-> il-code parse transform-il))
=======
(ns il-to-ld-converter.parser
  (:require [instaparse.core :as insta]
            [instaparse.transform :as insta-transform]
            [clojure.string :as str]
            [clojure.pprint :as pp]))

;; IL Grammar using Instaparse - ultra simplified approach
(def il-grammar
  "
  <S> = program
  program = (statement | <nl> | <ws>)*
  statement = (labeled-instr | instruction | comment)
  
  instruction = operation <ws-plus> operand (<ws> comment)?
  labeled-instr = label <ws-opt> operation <ws-plus> operand (<ws> comment)?
  
  label = identifier ':'
  operation = 'LD' | 'LDN' | 'ST' | 'STN' | 'S' | 'R' | 'AND' | 'ANDN' | 
              'OR' | 'ORN' | 'XOR' | 'XORN' | 'ADD' | 'SUB' | 'MUL' | 
              'DIV' | 'GT' | 'GE' | 'EQ' | 'NE' | 'LE' | 'LT' | 'JMP'
  
  operand = memory-address | identifier | numeric
  
  identifier = #'[a-zA-Z_][a-zA-Z0-9_]*'
  numeric = #'[+-]?[0-9]+(\\.[0-9]+)?'
  memory-address = #'%[IQM][XBWDL]?[0-9]+(\\.[0-9]+)?'
  
  comment = #';.*'
  <ws> = #'[ \\t]'
  <ws-opt> = #'[ \\t]*'
  <ws-plus> = #'[ \\t]+'
  <nl> = #'\\r?\\n'
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
                      {:error (insta/get-failure parsed)
                       :input il-code}))
      parsed)))

;; Transform parsed IL to a more usable structure
(defn transform-il [parsed]
  (insta-transform/transform
   {:program (fn [& statements]
               {:type :program
                :instructions (vec (remove nil? statements))})
    :statement identity
    :instruction (fn [op operand & rest]
                   {:type :instruction
                    :operation op
                    :operand operand})
    :labeled-instr (fn [label op operand & rest]
                     {:type :instruction
                      :label label
                      :operation op
                      :operand operand})
    :label (fn [id _] id) ; Remove the colon
    :operation identity
    :operand identity
    :identifier identity
    :numeric #(try (Float/parseFloat %) (catch Exception _ %))
    :memory-address identity
    :comment (fn [_] nil)}
   parsed))

;; High-level parsing function
(defn parse-il-program
  "Parse an IL program and transform it"
  [il-code]
  (-> il-code parse transform-il))
>>>>>>> 924db9b (WSL Debian)
