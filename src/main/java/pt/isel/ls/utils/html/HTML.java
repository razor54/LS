package pt.isel.ls.utils.html;


public class HTML extends HTMLElement {
    private HTMLBody<HTMLElement> body = new HTMLBody<>();
    private HTMLHead<HTMLElement> head = new HTMLHead<>();
    private String html;

    public HTML() {
        super(HTMLTypes.HTML);
        tabs = 0;
    }

    public HTML(String html) {
        super(HTMLTypes.HTML);
        this.html = html;
    }


    @Override
    protected void addTabs() {
        return;
    }

    @Override
    public String getName() {
        return html == null ? "<" + type + ">" + head.getName() + body.getName() + "<" + type + ">" : html;
    }


    public HTMLElement addChild(HTMLElement child) {
        return body.addChild(child);
    }

    @Override
    public String toString() {
        return getName();
    }

}
