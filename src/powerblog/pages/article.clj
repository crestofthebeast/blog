(ns powerblog.pages.article
  (:require [powerblog.layout :as layout]
            [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {}
        [:div.article-container
          [:article
           (md/render-html (:page/body page))]]))
