package pt.isel.ls.utils.html;

import org.junit.Test;


public class HTMLFormatterTest {

    private String string;

    @Test
    public void format_test() {
        string = HTMLElement.ELEMENT_TERMINATOR;
        String s = "<HTML>-<body>-<table>-</table>-</body>-</HTML>";
        System.out.println(HTMLFormatter.format(s));

    }
}