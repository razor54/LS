package pt.isel.ls.utils.html;


import pt.isel.ls.utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class HTMLElement {
    static final String ELEMENT_INITIATOR = "<";
    final static String ELEMENT_TERMINATOR = "</";

    final HTMLTypes type;
    private final Optional<HTMLRef> href;


    int tabs = 0;

    List<Pair<String, String>> attrs = new LinkedList<>();

    protected abstract void addTabs();

    protected HTMLElement(HTMLTypes type) {
        this.type = type;
        href = Optional.empty();
    }

    protected HTMLElement(HTMLTypes type, HTMLRef href) {
        this.type = type;
        this.href = Optional.of(href);
    }


    public abstract String getName();


    String getHref(String value) {
        return href
                .map(ref -> ref.With(value))
                .orElse(value);
    }

    public final HTMLElement withAttr(String type, String value) {
        attrs.add(new Pair<>(type, value));
        return this;
    }

    String writeAttrs() {
        StringBuilder builder = new StringBuilder();
        attrs.forEach(a -> builder.append(" ").append(a.toString()).append(" "));
        return builder.toString();
    }
}
