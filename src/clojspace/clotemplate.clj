(def title-word { "index" "ClojSpace User Hub", "about" "About ClojSpace", "help" "ClojSpace Help" , "register" "Register" })
(def uri-set #{"index" "about" "help" "register"} )

 
 
 (html/deftemplate t1 "static/site.html" [title contenti & extra]
[:div#header :p]  (html/content title)
[:div#error-register :p]  (html/content (apply str extra))
[:div#conten]  (html/html-content (apply  str (html/emit* (apply :content (apply :content (html/html-resource contenti)))))))

