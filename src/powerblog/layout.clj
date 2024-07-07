(ns powerblog.layout)

(def header
  [:header.prose.max-w-screen.mx-auto.pb-6
   [:nav.bg-deep-black.flex.items-center.justify-between.border-b.border-off-white.max-h-12.relative
    [:ul.list-none.pl-0
     [:li
      [:a {:href "/"} "home"]]]
    [:ul.list-none.pl-0.hidden.lg:block
     [:li
      [:a {:href "/"} "programming resources"]]]
    [:ul.list-none.pl-0.pr-2
     [:li
      [:a {:href "/about/"} "about"]]]]])

(defn layout [{:keys [title]} & content]
  [:html.bg-deep-black.font-ibm-plex-serif.text-off-white.p-8
   header
   [:head
    (when title [:title title])]
   [:body
    content]])
