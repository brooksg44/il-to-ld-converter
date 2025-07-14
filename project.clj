(defproject il-to-ld-converter "0.1.0-SNAPSHOT"
  :description "IEC 61131-3 Instruction List (IL) to Ladder Diagram (LD) Converter"
  :url "https://github.com/yourusername/il-to-ld-converter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [instaparse "1.5.0"]
                 ;;[instaparse-transform "0.3.1"] ; For transforming parsed data
                 [org.clojure/core.match "1.0.1"]
                 [org.clojure/core.cache "1.0.225"]
                 [cljfx "1.9.5"]]
  :main ^:skip-aot il-to-ld-converter.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
