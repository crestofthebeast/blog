(ns powerblog.layout)

(defn layout [{:keys [title]} & content]
  [:html.dark:bg-zinc-900
   [:head
    (when title [:title title])]
   [:body.py-8
    content]])

;; TODO: some kind of function to put pipes between things here
(def header
  [:header.mx-auto.dark:prose-invert.prose.mb-8
   [:a {:href "/"} "blackmoonkite"]
   [:span " | "]
   [:a {:href "blog-posts/learn-you-programming"} "programming resources"]])
