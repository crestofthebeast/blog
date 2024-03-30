(ns powerblog.layout)


(def header
  [:header.dark:prose-invert.prose.max-w-screen.mx-auto.pb-6
   [:nav.flex.items-center.justify-between.border-b.border-darkish-grey.dark:border-white.max-h-12.relative
    [:ul.list-none.pl-0
     [:li
      [:a {:href "/"} "home"]]]
    [:ul.list-none.pl-0.hidden.lg:block
     [:li
      [:a {:href "/"} "programming resources"]]]
    [:ul.list-none.pl-0
     [:li
      [:a {:href "/about/"} "about"]]]]])

(defn layout [{:keys [title]} & content]
  [:html.dark:bg-dark-grey.bg-beige.font-ibm-plex-sans.text-darkish-grey.p-8
   header
   [:head
    (when title [:title title])]
   [:body
    content]])
