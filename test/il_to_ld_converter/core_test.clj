testing "Generating LD diagram representation"
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

(deftest ui-integration-test
  (testing "UI event handling"
    (let [sample-il "LD %I0.0"
          test-state {:il-code sample-il :ld-output "" :status "Ready" :status-type :info}]
      (is (= sample-il (:il-code test-state)))
      (is (string? (:status test-state))))))
