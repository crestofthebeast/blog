(ns powerblog.pages.frontpage
  (:require [datomic.api :as d]
            [powerblog.layout :as layout]
            [powerpack.markdown :as md]))


(defn get-blog-posts [db]
  (->> (d/q '[:find [?e ...]
              :where
              [?e :blog-post/author]]
            db)
       (map #(d/entity db %))))

(defn render-page [context page]
  (let [blog-posts (get-blog-posts (:app/db context))]
    (layout/layout {:title "blackmoonkite"}
             [:article
              (md/render-html (:page/body page))
              [:h2 [:i18n ::blog-posts {:n (count blog-posts)}]]
              [:ul
               (for [blog-post (get-blog-posts (:app/db context))]
                 [:li [:a {:href (:page/uri blog-post)} (:page/title blog-post)]])]])))
