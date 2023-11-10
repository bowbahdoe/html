# html

Template processor for producing html.

**NOTE:** Template processors are currently a preview feature,
and as such releases of this library are tied to a specific 
release of Java. 

Expect to have to upgrade.

## Dependency Information

### Maven

```xml
<dependency>
    <groupId>dev.mccue</groupId>
    <artifactId>html</artifactId>
    <version>0.0.1-alpha1</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation("dev.mccue:html:0.0.1-alpha1")
}
```

## Usage

### Produce Basic HTML

```java
import dev.mccue.html.Html;
import static dev.mccue.html.Html.HTML;

void main() {
    var name = "Bob";
    var html = HTML."""
        <h1> Hello \{name} </h1>
        """;
    System.out.println(html);
}
```

### Produce HTML with sanitized values

```java
import dev.mccue.html.Html;
import static dev.mccue.html.Html.HTML;

void main() {
    var name = "<script>alert(\"hi\")</script>";
    var html = HTML."""
        <h1> Hello \{name} </h1>
        """;
    System.out.println(html);
}
```

### Produce HTML with un-sanitized values

```java
import dev.mccue.html.Html;
import static dev.mccue.html.Html.HTML;

void main() {
    var name = "<script>alert(\"hi\")</script>";
    var html = HTML."""
        <h1> Hello \{Html.raw(name)} </h1>
        """;
    System.out.println(html);
}
```

### Compose functions which produce html

```java
import dev.mccue.html.Html;
import static dev.mccue.html.Html.HTML;

Html page(Html body) {
    return HTML."""
        <html>
            <head>
               <script src="..."></script>
            </head>
            <body>
               \{body}
            </body>
        </html>
        """;
}

void main() {
    var name = "bob";
    var body = HTML."""
        <h1> Hello \{name} </h1>
        """;
    var page = page(body);
    System.out.println(page);
}
```

### Define a class which can be turned into html

```java
import dev.mccue.html.Html;
import dev.mccue.html.HtmlEncodable;

import static dev.mccue.html.Html.HTML;

record Person(String name, int age)
        implements HtmlEncodable {
    @Override
    public Html toHtml() {
        return HTML."<h1> \{name} is \{age} years old";
    }
}
```