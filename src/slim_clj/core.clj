(ns slim_clj.core
(:use [clojure.contrib.duck-streams :only (read-lines)]))

(defstruct html-node :depth :interpreted_type :interpreted :selector :attributes :content)

(defn- process-line [acc line]
  (let [[full-text 
         leading_whitespace 
         interpreted
         selector 
         full_attributes 
         attributes 
         content] (re-find #"^([\s]*)([-=;|]?)([\w]*)([(](.*?)[)])?(.*)$" line)]
    (if (or (= (.trim full-text) "") (= interpreted ";"))
      acc
      (conj acc (struct-map html-node :depth (count leading_whitespace), 
                                    :interpreted_type interpreted,
                                    :interpreted (or (= interpreted "=") (= interpreted "-")),
                                    :selector selector, 
                                    :attributes attributes, 
                                    :content (.trim content))))))

(defn- process-file [file-name line-accum]
  (reduce process-line line-accum (clojure.contrib.duck-streams/read-lines file-name)))

(def self-closing-tags
  #{"meta" "br" "link"})

(defn- attributes [attr]
  (if (nil? attr) "" (str " " attr)))

(defn- opening-tag [node]
  (if (contains? self-closing-tags (node :selector))
    (str "<" (node :selector) (attributes (node :attributes)) " />")
    (str "<" (node :selector) (attributes (node :attributes)) ">")))

(defn- closing-tag [node]
  (if (contains? self-closing-tags (node :selector)) "" (str "</" (node :selector) ">")))

(defn- interpreted-clojure [node]
  (load-string (node :content)))
  
(defn- content [node]
  (if (= "" (node :content)) "" (str (node :content))))

(defn- write-html [data stack]
  (if (empty? data)
    (reduce #(str %1 (closing-tag %2)) "" stack)
    (let [current (first data)]
    (if (<= (get current :depth -1) (get (first stack) :depth -2))
      (str (closing-tag (first stack)) (write-html data (rest stack)))
      (if (current :interpreted)
        (if (= "=" (current :interpreted_type)) 
          (str (interpreted-clojure current) (write-html (rest data) stack))
          (do (interpreted-clojure current) (str (write-html (rest data) stack))))
        (if (= (current :interpreted_type) "|")
          (str (content current) " " (write-html (rest data) stack))
          (str (opening-tag current) (content current) (write-html (rest data) (conj stack current)))))))))
    
(defn render-template [template]
  (write-html (process-file template []) '())
)