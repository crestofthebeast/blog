(ns powerblog.pages.teranoptia
  (:require [powerblog.pages.article :as article]
            [powerblog.layout :as layout]
            [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {:title "Teranoptia Playground"}
                 [:article.prose.max-w-screen-lg.mx-auto
                  (md/render-html (:page/body page))
                  [:blockquote.min-h-96.pl-0.border.border-gentle-black.font-teranoptia.break-all.text-wrap.text-6xl
                   {:contenteditable "true" :autocomplete "off" :autocorrect "off" :autocapitalize "off" :spellcheck "false"} "sttnr"]]))
