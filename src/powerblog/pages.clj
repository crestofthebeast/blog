(ns powerblog.pages
  (:require
   [powerblog.pages.article :as article]
   [powerblog.pages.about :as about]
   [powerblog.pages.blog-listing :as blog-listing]
   [powerblog.pages.frontpage :as frontpage]
   [powerblog.pages.blog-post :as blog-post]
   [powerblog.pages.dhole :as dhole]
   [powerblog.pages.tag :as tag]))

(defn render-page [context page]
  (case (:page/kind page)
    :page.kind/about (about/render-page context page)
    :page.kind/frontpage (frontpage/render-page context page)
    :page.kind/blog-post (blog-post/render-page context page)
    :page.kind/blog-listing (blog-listing/render-page context page)
    :page.kind/tag (tag/render-page context page)
    :page.kind/dhole (dhole/render-page context page)
    :page.kind/article (article/render-page context page)))
