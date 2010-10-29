(ns slim.test.core
  (:use [slim.core] :reload)
  (:use [clojure.test]))

(deftest slim
  (is (=
    (render-template "test/samples/basic.slim.html")
    "<h1>Hello Chas</h1>"))

  (is (=
    (render-template "test/samples/basic_with_attr.slim.html")
    "<h1 class=\"my_class\" id=\"my_id\">Hello Chas</h1>"))

  (is (=
    (render-template "test/samples/basic_with_delimeter.slim.html")
    "<p>Lorem Ipsum Dolor Sit </p>"))

  (is (=
    (render-template "test/samples/nested_tags.slim.html")
    "<div class='chas'><p>Lorem Ipsum</p></div>"))

  (is (=
    (render-template "test/samples/self_closing_tags.slim.html")
    "<br /><meta name=\"keyword\" content=\"clojure, templating, slim\" />"))

  (is (=
    (render-template "test/samples/comments_and_empty_lines.slim.html")
    "<p>My Paragraph</p>"))

  (is (=
    (render-template "test/samples/multiple_depths.slim.html")
    "<html><head><title>The Title</title></head><body><div><p>Welcome to Helloworldian</p></div></body></html>"))

  (is (=
    (render-template "test/samples/embedded_clojure.slim.html")
    "<h1>Hello, Chas</h1>"))
)


(deftest with_hiccup
  (is (=
    (render-template "test/samples/hiccup_integration.slim.html")
    "<div><h1 class=\"heading cats\" id=\"garfield\">Hello World</h1></div>"))
)

(deftest with_directive
  (is (=
    (render-template "test/samples/directive.slim.html")
    "<!DOCTYPE HTML><html><head></head><body></body></html>"))
)

(deftest with-xml_directive
  (is (=
    (render-template "test/samples/basic.slim.xml")
    "<?xml version='1.0' encoding='utf-8' ?><response><content>Hello World</content></response>"))
)

(deftest with-passed-args
  (is (=
    (render-template "test/samples/passed-args.slim.html" {:paragraph "Hello Passed Args"})
    "<div><p>Hello Passed Args</p></div>"))
    
  (is (=
    (render-template "test/samples/advanced_pass-args.slim.html" {:title "My Blog Title" :content "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."})
    "<!DOCTYPE HTML><html><head><title>My Blog Title</title></head><body><h1>My Blog Title</h1><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p></body></html>"))
)