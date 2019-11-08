(ns restapi.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [cider.nrepl :as cider-nrepl]
            [nrepl.server :refer [start-server stop-server]])
  (:gen-class))


; Simple Body Page
(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

; request-example
(defn request-example [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->>
              (pp/pprint req)
              (str "Request Object: " req))})

(defn hello-name [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->
              (pp/pprint req)
              (str "Hello 123 " (:name (:params req))))})


(defroutes app-routes
 (GET "/" [] simple-body-page)
 (GET "/request" [] request-example)
 (GET "/hello" [] hello-name)
 (route/not-found "Error, page not found"))

(defn- start-nrepl [{:keys [port]}]
  (start-server
    :port port
    :handler cider-nrepl/cider-nrepl-handler))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [port 3001]
    (start-nrepl {:port 3101})
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
