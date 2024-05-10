(ns powerblog.pages.teranoptia
  (:require [powerblog.pages.article :as article]
            [powerblog.layout :as layout]
            [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {:title "Teranoptia Playground"}
                 [:article.prose.max-w-screen-md.mx-auto
                  (md/render-html (:page/body page))
                  [:blockquote.min-h-96.border.border-gentle-black.font-teranoptia.break-all.text-wrap {:contenteditable "true"}]]))
