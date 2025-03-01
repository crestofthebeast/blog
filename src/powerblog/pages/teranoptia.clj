(ns powerblog.pages.teranoptia
  (:require
   [powerblog.layout :as layout]
   [powerpack.markdown :as md]))

(defn render-page [context page]
  (layout/layout {:title "Teranoptia Playground"}
                 [:article
                  (md/render-html (:page/body page))
                  [:blockquote
                   {:class "min-h-96 pl-0 font-teranoptia break-all text-wrap text-6xl focus:bg-hl-sepia/40 outline-none my-0"
                    :contenteditable "true" :autocomplete "off" :autocorrect "off" :autocapitalize "off" :spellcheck "false"} "sttnr"]]))
