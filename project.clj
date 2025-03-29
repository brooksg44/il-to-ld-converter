(defproject il-to-ld-converter "0.1.0-SNAPSHOT"
  :description "IEC 61131-3 Instruction List (IL) to Ladder Diagram (LD) Converter"
  :url "https://github.com/yourusername/il-to-ld-converter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [instaparse "1.4.12"]
                 [instaparse-transform "0.3.1"] ; For transforming parsed data
                 [org.clojure/core.match "1.0.1"]]
  :main ^:skip-aot il-to-ld-converter.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
