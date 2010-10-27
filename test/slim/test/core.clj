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


(deftest slim-with_hiccup
  (is (=
    (render-template "test/samples/hiccup_integration.slim.html")
    "<div><h1 class=\"heading cats\" id=\"garfield\">Hello World</h1></div>"))
)