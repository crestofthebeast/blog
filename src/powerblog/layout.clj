(ns powerblog.layout)


(def header
  [:header.prose.max-w-screen.mx-auto.pb-6
   [:nav.flex.items-center.justify-between.border-b.border-gentle-black.max-h-12.relative
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
  [:html.bg-sepia.font-ibm-plex-sans.text-gentle-black.p-8
   header
   [:head
    (when title [:title title])]
   [:body
    content]])
