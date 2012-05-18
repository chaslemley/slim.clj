(ns slim.core
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defstruct html-node :depth :interpreted_type :interpreted :selector :attributes :content)

(defn- process-line [acc line]
  (let [[full-text
         directive
         leading_whitespace
         interpreted
         selector
         full_attributes
         attributes
         content] (re-find #"^(!!!)?([\s]*)([-=;|]?)([\w]*)([(](.*?)[)])?(.*)$" line)]
    (if (or (= (.trim full-text) "") (= interpreted ";"))
      acc
      (conj acc (struct-map html-node
                  :depth (count leading_whitespace),
                  :interpreted_type interpreted,
                  :interpreted (or (= interpreted "=") (= interpreted "-")),
                  :directive? (= directive "!!!"),
                  :selector selector,
                  :attributes attributes,
                  :content (.trim content))))))

(defn- read-lines [f]
  (with-open [f (io/reader (io/file f))]
    (doall (line-seq f))))

(defn- process-file [file-name line-accum]
  (reduce process-line line-accum (read-lines file-name)))

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

(defn- directive [node]
  (cond
    (= (node :selector) "strict") "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
    (= (node :selector) "html")   "<!DOCTYPE HTML>"
    (= (node :selector) "xml")    "<?xml version='1.0' encoding='utf-8' ?>"))


(defn- write-html [data stack]
  (if (empty? data)
    (reduce #(str %1 (closing-tag %2)) "" stack)
    (let [current (first data)]
      (if (<= (get current :depth -1) (get (first stack) :depth -2))
        (str (closing-tag (first stack)) (write-html data (rest stack)))
        (if (current :directive?)
          (str (directive current) (write-html (rest data) stack))
          (if (current :interpreted)
            (if (= "=" (current :interpreted_type))
              (str (interpreted-clojure current) (write-html (rest data) stack))
              (do (interpreted-clojure current) (str (write-html (rest data) stack))))
            (if (= (current :interpreted_type) "|")
              (str (content current) " " (write-html (rest data) stack))
              (str (opening-tag current) (content current) (write-html (rest data) (conj stack current))))))))))

(defn render-template [template & data]
  (use 'hiccup.core)
  (intern *ns* 'locals (fn [s] (get (first data) s)))

  (write-html (process-file template []) '()))
