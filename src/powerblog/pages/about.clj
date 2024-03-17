(ns powerblog.pages.about
  (:require [powerblog.pages.article :as article]))

(defn render-page [context page]
  (article/render-page context page))
