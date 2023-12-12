package dev.mccue.html;

/**
 * Contents to be inserted into the Html raw and not and escaped.
 * @param contents The contents to insert into the HTML.
 */
record Raw(String contents) {
    Raw(String contents) {
        this.contents = contents == null ? "" : contents;
    }
}

