package pt.isel.ls.utils.html;


public class HTMLHead<T extends HTMLElement> extends AbstractNonLeafElement<T, HTMLElement> {

    HTMLHead() {
        super(HTMLTypes.HEAD);
    }

    @Override
    public HTMLElement addChild(HTMLElement child) {
        return this;
    }

    @Override
    public String getName() {
        return "<head> <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/head.css\"> </head>";
    }

}
