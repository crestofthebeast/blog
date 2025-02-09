(ns powerblog.layout)

(def header
  [:header.navbar
   [:nav
    [:ul
     [:li
      [:a {:href "/"} "home"]]]
    [:ul
     [:li
      [:a {:href "/about/"} "about"]]]]])

(defn layout [{:keys [title]} & content]
  [:html
   header
   [:head
    (when title [:title title])]
   [:body
    content]])
