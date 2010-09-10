(ns model.model
   (:require [clojure.contrib.sql :as sql]))
   
       (def db {:classname "com.mysql.jdbc.Driver"
          :subprotocol "mysql"
          :subname "//localhost:3306/cloj_space_development"
          :user "deuser"
          :password "depass"})

       (defn drop-users []
   (sql/drop-table :users))    
          
          (defn create-users []
                    (drop-users)
            (sql/create-table
                     :users
         [:id :integer "PRIMARY KEY" "AUTO_INCREMENT"]
        [:screen_name "varchar(25)"]
        [:email "varchar(35)"]
        [:password "varchar(30)"]
        [:created_at "timestamp"]
        [:updated_at "timestamp"]))

(def screen-name-min-length 4)
(def screen-name-max-length 20)
(def password-min-length 4)
(def password-max-length 40)


   (defn insert-user [screen_name email password]
   (let [sql "insert into users (screen_name,email,password) values (? ,?, ?)"] 
   (sql/with-connection db
     (sql/do-prepared sql [screen_name email password] ))))
     
     
  (defn find-by-screen-name [screen_name]
  (sql/with-connection db 
    (sql/with-query-results rs [ "select * from users where screen_name=?" screen_name ]  
       (apply :screen-name rs))))
    
  (defn find-by-email [email]
  (sql/with-connection db 
    (sql/with-query-results rs [ "select * from users where email=?" email ]  
       (apply :email rs))))
       
  (defn uniqueness? [email screen-name]
  (and (nil? (find-by-screen-name screen-name))(nil? (find-by-email email))))
  
  (defn screen-name-valid? [screen-name]
  (and (> (count screen-name ) screen-name-min-length) (< (count screen-name) screen-name-max-length)
   (re-find #"^[A-Z0-9_]*$/i" screen-name)))
  
   (defn password-within? [password]
  (and (> (count password ) password-min-length) (< (count password) password-max-length)))
  
  (defn email-valid? [email]
  (and (not (nil? email))   (re-find #"^[A-Z0-9_%-]+@([A-Z0-9-]+\.)+[A-Z]{2,4}$/i" email) )) 
  
  (defn add-user [[screen-name email password]]
  (and (uniqueness? email screen-name) (screen-name-within? screen-name) 
               (password-within? password)(email-presence? email))
               (insert-user screen-name email password)
     )
     
     
    ; (create-users)
    ;; (create-posts)
    ;; (create-comments))
   ;;(doseq [doon [create-members create-posts create-comments]]
   ;;     (sql/with-connection db #(doon)))
        

(add-user "me" "" "a")
   
   
   (sql/with-connection db
  (create-users))
