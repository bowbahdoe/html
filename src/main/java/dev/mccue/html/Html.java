package dev.mccue.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Html implements HtmlEncodable {
    public static final StringTemplate.Processor<Html, RuntimeException> HTML
            = HtmlProcessor.INSTANCE;

    final List<String> fragments;

    Html(List<String> fragments) {
        this.fragments = fragments;
    }

    public static Html of(List<?> fragments) {
        var strings = new ArrayList<String>();
        for (var fragment : fragments) {
            HtmlProcessor.escapeValue(strings, fragment);
        }
        return new Html(Collections.unmodifiableList(strings));
    }

    public static Object raw(String contents) {
        return new Raw(contents);
    }

    @Override
    public String toString() {
        return String.join("", fragments);
    }

    @Override
    public Html toHtml() {
        return this;
    }
}
