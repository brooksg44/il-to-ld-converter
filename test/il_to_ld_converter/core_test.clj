(ns il-to-ld-converter.core-test
  (:require [clojure.test :refer :all]
            [il-to-ld-converter.core :refer :all]
            [il-to-ld-converter.parser :as parser]
            [il-to-ld-converter.converter :as converter]))

(deftest parse-il-program-test
  (testing "Parsing a simple IL program"
    (let [sample-il "LD %I0.0"
          parsed (parser/parse-il-program sample-il)]
      (is (= :program (get parsed :type)))
      (is (= 1 (count (:instructions parsed))))
      (is (= "LD" (get-in parsed [:instructions 0 :operation]))))))

(deftest convert-il-to-ld-test
  (testing "Converting IL to LD representation"
    (let [sample-il "LD %I0.0"
          parsed (parser/parse-il-program sample-il)
          ld-program (converter/convert-il-to-ld parsed)]
      (is (= :ld-program (get ld-program :type)))
      (is (= 1 (count (:rungs ld-program))))
      (is (= :contact-load (get-in ld-program [:rungs 0 :type]))))))

(deftest generate-ld-diagram-test
  (testing "Generating LD diagram representation"
    (let [sample-il "LD %I0.0"
          parsed (parser/parse-il-program sample-il)
          ld-program (converter/convert-il-to-ld parsed)
          diagram (converter/generate-ld-diagram ld-program)]
      (is (string? diagram))
      (is (re-find #"Rung 1" diagram)))))

(deftest end-to-end-conversion-test
  (testing "End-to-end IL to LD conversion"
    (let [sample-il "LD %I0.0\nAND %I0.1\nST %Q0.0"
          ld-program (convert-il-program sample-il)]
      (is (= :ld-program (get ld-program :type)))
      (is (= 3 (count (:rungs ld-program)))))))

(defn run-tests []
  (run-tests 'il-to-ld-converter.core-test))
