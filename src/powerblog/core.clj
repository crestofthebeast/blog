(ns powerblog.core
  (:require
   [powerblog.ingest :as ingest]
   [powerblog.pages :as pages]
   [powerpack.markdown :as md])
  (:import
   [com.vladsch.flexmark.ext.anchorlink AnchorLinkExtension]
   [com.vladsch.flexmark.ext.attributes AttributesExtension]
   [com.vladsch.flexmark.ext.autolink AutolinkExtension]
   [com.vladsch.flexmark.ext.footnotes FootnoteExtension]
   [com.vladsch.flexmark.ext.gfm.strikethrough StrikethroughSubscriptExtension]
   [com.vladsch.flexmark.ext.gitlab GitLabExtension]
   [com.vladsch.flexmark.ext.aside AsideExtension]
   [com.vladsch.flexmark.ext.tables TablesExtension]
   [com.vladsch.flexmark.ext.typographic TypographicExtension]
   [com.vladsch.flexmark.parser Parser]
   [com.vladsch.flexmark.util.data MutableDataSet]))

;; (def aside-ext
;;   (.set (AsideExtension/create) :INTERRUPTS_PARAGRAPH false))

(def flexmark-opts
  (-> (MutableDataSet.)
      (.set AttributesExtension/ASSIGN_TEXT_ATTRIBUTES true)
      (.set AttributesExtension/FENCED_CODE_INFO_ATTRIBUTES true)
      (.set Parser/EXTENSIONS [(AutolinkExtension/create)
                               (AnchorLinkExtension/create)
                               (AttributesExtension/create)
                               (FootnoteExtension/create)
                               (AsideExtension/create)
                               (GitLabExtension/create)
                               (StrikethroughSubscriptExtension/create)
                               (TablesExtension/create)
                               (TypographicExtension/create)])))

(alter-var-root #'md/flexmark-opts (constantly flexmark-opts))

(def config
  {:site/title "avery j"
   :powerpack/render-page #'pages/render-page
   :powerpack/create-ingest-tx #'ingest/create-tx
   :powerpack/on-ingested #'ingest/on-ingested

   :optimus/bundles {"app.css"
                     {:public-dir "public"
                      :paths ["/styles.css"]}}

   :optimus/assets [{:public-dir "public"
                     :paths [#".*\.png"]}]

   :m1p/dictionaries {:en ["src/powerblog/i18n/en.edn"]}})
