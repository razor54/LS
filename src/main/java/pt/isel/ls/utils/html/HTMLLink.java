package pt.isel.ls.utils.html;


public class HTMLLink extends HTMLElement {

    private final String reference;

    protected HTMLLink(String reference) {
        super(HTMLTypes.LINK);
        this.reference = reference;
    }

    protected HTMLLink(HTMLRef href, String reference) {
        super(HTMLTypes.LINK, href);
        this.reference = reference;
    }

    @Override
    protected void addTabs() {

    }

    @Override
    public String getName() {
        return null;
//        "<link href=" + reference + ">" +NEW_ELEMENT +" "+NEW_ELEMENT +"</link>" + NEW_ELEMENT;
    }
}
