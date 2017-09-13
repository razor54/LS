package pt.isel.ls.model.data.entities;

import pt.isel.ls.utils.html.HTMLElement;


public class NoEntity extends Entity {
    public NoEntity(String[] tableHeaders) {
        super(tableHeaders);
    }

    @Override
    public HTMLElement getHtmlElement() {
        return null;
    }

    @Override
    public String getJson() {
        return null;
    }

    @Override
    public String getPlaintText() {
        return null;
    }
}
