(ns powerblog.layout)

(def header
  [:header.navbar
   [:nav
    [:h1.title-logo "BLACKMOONKITE"]
    [:ul
     [:li
      [:a {:href "/"} "home"]]]
    [:ul
     [:li
      [:a {:href "/about/"} "about"]]]]])

(def footer
  [:footer
   [:p "Footer."]])

(defn layout [{:keys [title]} & content]
  [:html
   header
   [:head
    (when title [:title title])]
   [:body
    content]
   ])
