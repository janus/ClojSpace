  (ns clojspace.validation
  
  (:use form-dot-clj.core)
  (:use form-dot-clj.jquery-tools)
  (:require [clojspace.model :as model]))
   
   
(def screen-name-min-length 4)
(def screen-name-max-length 20)
(def password-min-length 4)
(def password-max-length 40)

  (defn uniqueness? [email screen-name]
  (and (nil? (seq (model/find-by-screen-name screen-name)))(nil? (seq (model/find-by-email email)))))
    
  (defn screen-name-valid? [screen-name]
  (and (> (count screen-name ) screen-name-min-length) (< (count screen-name) screen-name-max-length)
   (re-find #"^[A-Z0-9_]*$/i" screen-name)))
  
   (defn password-within? [password]
  (and (> (count password ) password-min-length) (< (count password) password-max-length)))
  
  (defn email-valid? [email]
  (and (not (nil? email))   (re-find #"^[A-Z0-9_%-]+@([A-Z0-9-]+\.)+[A-Z]{2,4}$/i" email) )) 
  
  (defn add-user [params]
  (let [email (params "email") screen-name (params "screen_name") password (params "password")]
  (if (and (uniqueness? email screen-name) (screen-name-valid? screen-name) 
               (password-within? password)(email-valid? email))
               (model/insert-user screen-name email password)
     )))
     
  (defn login-data [params]
  (let [screen-name (params "screen_name") password (params "password")]
  (not (nil? (seq (model/find-by-screen-name-password screen-name password))))))
     