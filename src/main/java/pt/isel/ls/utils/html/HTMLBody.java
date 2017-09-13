package pt.isel.ls.utils.html;


public class HTMLBody<T extends HTMLElement> extends AbstractNonLeafElement<T, HTMLBody<T>> {
    HTMLBody() {
        super(HTMLTypes.BODY);
        tabs = 1;
    }

    @Override
    public String getName() {
        return formName();
    }


    @Override
    public HTMLBody<T> addChild(T child) {
        list.add(child);
        return this;
    }


}
