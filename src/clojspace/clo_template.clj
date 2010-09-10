(ns clojspace.clo-template
(:require [net.cgrand.enlive-html :as html]))

(def title-word { "index" "ClojSpace User Hub", "about" "About ClojSpace", "help" "ClojSpace Help" , "register" "Register" , "login" "ClojSpace"})
(def uri-set #{"index" "about" "help" "register" "login"} )

 
 
 (html/deftemplate t1 "static/site.html" [title contenti & extra]
[:div#conten :p]  (html/content title)
[:div#error-message :p]  (html/html-content (apply str extra))
[:div#conten :div]  (html/html-content (apply  str (html/emit* (apply :content (apply :content (html/html-resource contenti)))))))

