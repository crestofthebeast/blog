(ns powerblog.layout)


;; TODO: some kind of function to put pipes between things here
(def header
  [:header.mx-auto.dark:prose-invert.prose.mb-4.space-x-4
   [:a {:href "/"} "home"]
   [:a.flex-auto.self-end {:href "/about/"} "about"]])

(defn layout [{:keys [title]} & content]
  [:html.dark:bg-dark-grey.bg-beige.font-ibm-plex-sans.text-darkish-grey.p-8
   header
   [:head
    (when title [:title title])]
   [:body.py-8
    content]])
