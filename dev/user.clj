(ns user
  (:require
   [powerblog.core :as blog]
   [powerpack.dev :as dev]))

(defmethod dev/configure! :default []
  blog/config)  ;; 1

(comment
  (def p (p/open {:launcher :vs-code}))
  {:hello #{:world :mars :venus}
   :goodbye #{:love :my-only-friend}}

  (comment
    (set! *print-namespace-maps* false)

    (dev/start)   ;; 2
    (dev/stop)    ;; 3
    (dev/reset)   ;; 4

    (def app (dev/get-app)) ;; 5

    (require '[datomic.api :as d])

    (def db (d/db (:datomic/conn app)))

    (->> (d/entity db [:page/uri "blog-posts/first-post/"])
         :blog-post/author
         (into {}))))
