package dev.mccue.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

enum HtmlProcessor implements StringTemplate.Processor<Html, RuntimeException> {
    INSTANCE;

    // https://stackoverflow.com/questions/1265282/what-is-the-recommended-way-to-escape-html-symbols-in-plain-java
    static String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '\'' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();

    }


    static void escapeValue(ArrayList<String> strings, Object value) {
        switch (value) {
            case null -> strings.add("");
            case Raw raw -> strings.add(raw.contents());
            case Html html -> strings.addAll(html.fragments);
            case HtmlEncodable toHtml -> strings.addAll(toHtml.toHtml().fragments);
            case Collection<?> valueList -> {
                for (var subValue : valueList) {
                    escapeValue(strings, subValue);
                }
            }
            case Stream<?> valueStream -> valueStream.forEach(subValue -> escapeValue(strings, subValue));
            default -> strings.add(escapeHTML(value.toString()));
        }
    }

    @Override
    public Html process(StringTemplate stringTemplate) throws RuntimeException {
        var fragments = stringTemplate.fragments();
        var values = stringTemplate.values();

        int fragmentsSize = fragments.size();
        int valuesSize = values.size();
        if (fragmentsSize == 1) {
            return new Html(List.of(fragments.get(0)));
        } else {
            int size = fragmentsSize + valuesSize;
            var strings = new ArrayList<String>(size);

            int i;
            for(i = 0; i < valuesSize; ++i) {
                strings.add(fragments.get(i));
                escapeValue(strings, values.get(i));
            }

            strings.add(fragments.get(i));
            return new Html(Collections.unmodifiableList(strings));
        }
    }

    @Override
    public String toString() {
        return "HtmlProcessor[]";
    }
}

