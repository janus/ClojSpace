
(ns done

(:use compojure.core
         ring.adapter.jetty)
         
(:require  [net.cgrand.enlive-html :as html])
(:require  [compojure.route :as route])
)


(def title-word { "index" "ClojSpace User Hub", "about" "About ClojSpace", "help" "ClojSpace Help" , "register" "Register" })
(def uri-set #{"index" "about" "help" "register"} )


(html/deftemplate rend "clojspace/isite.html" [title cont]
[:div#header :p]  (html/content  title)
[:div#content :p]    (html/content (apply str (html/emit* (apply :content (apply :content (html/html-resource cont)))))))

(defroutes hii

(GET  ["/:uri" :uri #".*"] [uri]
(if (string? uri)
(rend (title-word uri) (str "clojspace/views/" uri ".html")))))

(run-jetty hii {:port 8080})