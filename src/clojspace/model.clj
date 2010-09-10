(ns clojspace.model
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




   (defn insert-user [screen_name email password]
   (let [created_at (java.util.Date.) updated_at (java.util.Date.)  sql "insert into users (screen_name,email,password,created_at,updated_at) values (? ,?, ?,?,?)"] 
   (sql/with-connection db
     (sql/do-prepared sql [screen_name email password created_at updated_at] ))))
     
     
     
  (defn find-by-screen-name [screen_name]
  (sql/with-connection db 
    (sql/with-query-results rs [ "select * from users where screen_name=?" screen_name ]  
       (apply vector rs))))
    
  (defn find-by-email [email]
  (sql/with-connection db 
    (sql/with-query-results rs [ "select * from users where email=?" email ]  
       (apply vector rs))))
       
  (defn find-user-id [ params]
  (let [screen_name (params "screen_name") ]
   (sql/with-connection db 
    (sql/with-query-results rs [ "select id from users where screen_name=? " screen_name]  
       (apply vector rs)))))

(defn find-by-screen-name-password [ screen_name password]
     (sql/with-connection db 
    (sql/with-query-results rs [ "select id from users where screen_name=?  and password=?" screen_name password]  
       (apply vector rs))))
       
   ;(pr-str (find-by-screen-name-password "Janus" "google"))
     ;;(find-by-screen-name "EMEKA")
  ;;(add-user "EMEKA" "emekamicro@gmail.com" "emekas")
    ; (create-users)
    ;; (create-posts)
    ;; (create-comments))
   ;;(doseq [doon [create-members create-posts create-comments]]
   ;;     (sql/with-connection db #(doon)))
        

;;(add-user "me" "" "a")
   
   
  ; (sql/with-connection db
  ;;(create-users))
