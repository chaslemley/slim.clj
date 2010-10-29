# slim.clj

Simple Templating for Clojure
[Slim]: http://github.com/stonean/slim  "http://github.com/stonean/slim"
[Hiccup]: http://github.com/weavejester/hiccup "http://github.com/weavejester/hiccup"

based on Andrew Stone's [Slim]

## Usage

Create a slim template file 

    ; content of views/template.html.slim
    - (defn greeting [name] (str "Hello, " name))
    
    div
      h1
        = (greeting "Slim.clj")
      p(id='foo' class='bar') Hello World

Use the render-html function to convert the file to html

    user=> (use 'slim.core)
    nil

    user=> (render-template "views/template.html.slim")
    "<div><h1>Hello, Slim.clj</h1><p id='foo' class='bar'>Hello World</p></div>"

## Examples

### Integration with [Hiccup]

    ; content of views/hiccup.html.slim
    div
      = (html [:h1#garfield.heading.cats "Hello World"])

Produces

    "<div><h1 class=\"heading cats\" id=\"garfield\">Hello World</h1></div>"

### Doctypes

    ; HTML5
    !!! html
    ; strict
    !!! strict
    ; xml
    !!! xml

Produces

    <!DOCTYPE HTML>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    <?xml version='1.0' encoding='utf-8' ?>

## Installation

### Leiningen

    [slim "0.1.2"]

## License

Distributed under the Eclipse Public License, the same as Clojure.
