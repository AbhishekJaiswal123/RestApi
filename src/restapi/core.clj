(ns restapi.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [cider.nrepl :as cider-nrepl]
            [clojure.walk :as walk]
            [nrepl.server :refer [start-server stop-server]]
            [clojure.core.async :as a :refer [>! <! >!! <!! go chan buffer close! thread alts! alts!! timeout]])
  (:gen-class))


(defn- wrap-uri-check [expected-uri handler]
  (fn [{:keys [uri] :as req}]
    (when (= uri expected-uri)
      (handler req))))

(defn hello-handler [req]
  (wrap-uri-check "/hello" (fn [req] {:status  200
                                      :headers {"Content-Type" "text/html"}
                                      :body    (->
                                                 (pp/pprint req)
                                                 (str "Hello 12224 " (:name (:params req))))})))

; Simple Body Page
(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello Worldsdsd"})

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
              (str "Hello 12223 " (:name (:params req))))})


(defroutes app-routes
 (GET "/" [] simple-body-page)
 (GET "/request" [] request-example)
 (GET "/hello" [] hello-handler)
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