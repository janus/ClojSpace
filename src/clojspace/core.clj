(ns clojspace.core
 (:use compojure.core
         ring.adapter.jetty
         ring.util.response
         ring.middleware.session
                 )
  (:require [compojure.route :as route])
  (:require [clojure.contrib.sql :as sql])
  (:require [clojspace.model :as model])
  (:require [clojspace.validation :as valid])
  (:require [clojspace.clo-template :as temp])
  (:require [net.cgrand.enlive-html :as html]))


(defroutes example-routes

(GET ["/:uri"  :uri #".*"] [uri]
(if (temp/uri-set uri)
(temp/t1  (temp/title-word  uri)  (str "static/"  uri ".html"))))
 ;{session :session, params :params}
 ;;             {:session (assoc session :email (params "email"))
  ;;       :body 
(POST "/login" {session :session, params :params}
(if (valid/login-data params)
{session (assoc session :id ((model/find-user-id params) 0) )
   :body   (temp/t1 (str "Welcome  " (params "screen_name") )  "static/index.html"  )}

        (temp/t1  "ClojSpace"  "static/register.html"  "<div id=errorExplanation ><p>Wrong password or user name</p></div>" )))



(POST "/register"  {session :session,params :params}
(if(valid/add-user  params)
 {session (assoc session :id ((model/find-user-id params) 0) )
   :body   (temp/t1 (str "User  " (params "screen_name") "   created")  "static/index.html"  )}

        (temp/t1  "ClojSpace"  "static/register.html"  "<div id=errorExplanation ><p>Either your name or email is already taken</p><p> Or Name has leass than four characters</p><p> Or email not standard </p></div>" ))))

 (defroutes handler
   (GET "/" [] "A normal route")
   (route/files "/" {:root "./static"})
   (route/not-found "Not found"))
   
(defroutes app example-routes handler )


(run-jetty (var app) {:port 8085})




