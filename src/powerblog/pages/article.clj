(ns powerblog.pages.article
  (:require [powerblog.layout :as layout]
            [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {}
          [:article.prose.max-w-screen-md.mx-auto
           (md/render-html (:page/body page))
           [:script {:async true
                     :data-emit-metadata "0"
                     :crossorigin "anonymous"
                     :data-strict "0"
                     :data-mapping "title"
                     :src "https://giscus.app/client.js"
                     :data-lang "en"
                     :data-category-id "DIC_kwDOLhHFDM4Cfzq5"
                     :data-repo "crestofthebeast/blog"
                     :data-repo-id "R_kgDOLhHFDA"
                     :data-category "Announcements"
                     :data-input-position "top"
                     :data-theme "light"
                     :data-loading "lazy"
                     :data-reactions-enabled "0"}]]))