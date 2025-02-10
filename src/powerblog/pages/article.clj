(ns powerblog.pages.article
  (:require [powerblog.layout :as layout]
            [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {}
          [:article
           (md/render-html (:page/body page))]))
