package pt.isel.ls.utils.html;


import java.util.LinkedList;
import java.util.List;

abstract class AbstractNonLeafElement<T extends HTMLElement, U extends HTMLElement> extends HTMLElement {

    List<T> list;

    public abstract U addChild(T child);

    @Override
    protected void addTabs() {

    }

    AbstractNonLeafElement(HTMLTypes type) {
        super(type);
        list = new LinkedList<>();
    }

    String formName() {
        StringBuilder res = new StringBuilder();
        for (T t : list) {
            res.append(t.getName());
        }
        return "<" + type + writeAttrs() + ">" + getHref(res.toString()) + "</" + type + ">";
    }

    @Override
    public String getName() {
        return formName();
    }
}
