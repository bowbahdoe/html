package dev.mccue.html;

/**
 * Something which can be encoded as {@link Html}.
 */
public interface HtmlEncodable {
    /**
     * Convert to {@link Html}.
     * @return {@link Html}.
     */
    Html toHtml();
}
