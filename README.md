# slim.clj

Simple Templating for Clojure
[Slim]: http://github.com/stonean/slim  "http://github.com/stonean/slim"
[Hiccup]: http://github.com/weavejester/hiccup "http://github.com/weavejester/hiccup"

based on Andrew Stone's [Slim]

## Usage

####1. Create a slim template file 

    ; content of views/template.html.slim
    - (defn greeting [name] (str "Hello, " name))
    
    div
      h1
        = (greeting "Slim.clj")
      p(id='foo' class='bar') Hello World

####2. Use the render-html function to convert the file to html

    user=> (use 'slim.core)
    nil

    user=> (render-template "views/template.html.slim")
    "<div><h1>Hello, Slim.clj</h1><p id='foo' class='bar'>Hello World</p></div>"

####3. Slim.clj also includes integration with [Hiccup]

    ; content of views/hiccup.html.slim
    div
      = (html [:h1#garfield.heading.cats "Hello World"])
<br />
     user=> (render-template "views/hiccup.html.slim")
    "<div><h1 class=\"heading cats\" id=\"garfield\">Hello World</h1></div>"

## Installation

### Leiningen

    [slim "0.1.1"]

## License

Distributed under the Eclipse Public License, the same as Clojure.
