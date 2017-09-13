package pt.isel.ls.utils.html;

public class HTMLFormatter {

    private static final String NEW_LINE = "\n";

    // ugly version
    public static <T extends HTMLElement> String format(T html) {
        int tabs = 0;

        String[] elems = html.getName().split("");
        for (int i = 0; i < elems.length; i++) {
            elems[i] += (NEW_LINE);

            // increment or decrement depending on the terminal createStudent the html
            String elem = elems[i];
            if (elem.contains(HTMLElement.ELEMENT_TERMINATOR)) {
                --tabs;
                elems[i] = insertTabs(tabs, elems[i]);

            } else if (elem.contains(HTMLElement.ELEMENT_INITIATOR)) {
                elems[i] = insertTabs(tabs, elems[i]);
                ++tabs;
            } else elems[i] = insertTabs(tabs, elems[i]);

        }
        StringBuilder sb = new StringBuilder();
        for (String elem : elems) {
            sb.append(elem);
        }
        return sb.toString();

    }

    public static String format(String s) {
        int tabs = 0;

        String[] elems =s.split("-");
        for (int i = 0; i < elems.length; i++) {
            elems[i] += (NEW_LINE);

            // increment or decrement depending on the terminal createStudent the html
            String elem = elems[i];
            if (elem.contains(HTMLElement.ELEMENT_TERMINATOR)) {
                --tabs;
                elems[i] = insertTabs(tabs, elems[i]);

            } else if (elem.contains(HTMLElement.ELEMENT_INITIATOR)) {
                elems[i] = insertTabs(tabs, elems[i]);
                ++tabs;
            } else elems[i] = insertTabs(tabs, elems[i]);

        }
        StringBuilder sb = new StringBuilder();
        for (String elem : elems) {
            sb.append(elem);
        }
        return sb.toString();

    }

    public static String insertTabs(int tabs, String html) {
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < tabs; i++) {
            t.append("\t");
        }
        return t + html;
    }
}
