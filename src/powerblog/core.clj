(ns powerblog.core
  (:require 
   [powerblog.ingest :as ingest]
   [powerblog.pages :as pages]))


(def config
  {:site/title "avery's blog"
   :powerpack/render-page #'pages/render-page
   :powerpack/create-ingest-tx #'ingest/create-tx
   :powerpack/on-ingested #'ingest/on-ingested

   :optimus/bundles {"app.css"
                     {:public-dir "public"
                      :paths ["/styles.css"]}}
   
   :optimus/assets [{:public-dir "public"
                     :paths [#".*\.png"]}]
   
   :m1p/dictionaries {:en ["src/powerblog/i18n/en.edn"]}})
