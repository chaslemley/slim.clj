# slim

Simple Templating for Clojure

## Usage

    div
      p(id='foo' class='bar') Hello World



    user=> (use 'hiccup.core)
    nil
    user=> (render-template "views/template.html.slim")
    "<div><p id='foo' class='bar'>Hello World</p></div>"

## Installation


## License

Distributed under the Eclipse Public License, the same as Clojure.
