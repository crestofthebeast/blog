(ns powerblog.pages.article
  (:require [powerblog.layout :as layout]
            [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {}
          [:article.prose.max-w-screen-md.mx-auto
           (md/render-html (:page/body page))]))
