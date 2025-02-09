(ns powerblog.pages.article
  (:require [powerblog.layout :as layout]
            [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {}
          [
           (md/render-html (:page/body page))]))
