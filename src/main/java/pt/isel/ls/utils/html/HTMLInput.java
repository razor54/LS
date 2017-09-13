package pt.isel.ls.utils.html;

/**
 * Created by Leonardo Freire on 06/06/2017.
 */
public class HTMLInput extends HTMLElement {

    public HTMLInput() {
        super(HTMLTypes.INPUT);
    }

    protected HTMLInput(HTMLRef href) {
        super(HTMLTypes.INPUT, href);
    }

    @Override
    protected void addTabs() {

    }

    @Override
    public String getName() {
        return "<" + type + writeAttrs() + "/>";
    }
}
