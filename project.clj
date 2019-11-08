(defproject restapi "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :local-repo ".m2"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [http-kit "2.4.0-alpha4"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/data.json "0.2.6"]
                 [nrepl "0.6.0"]
                 [cider/cider-nrepl "0.22.4"]]
  :main ^:skip-aot restapi.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
