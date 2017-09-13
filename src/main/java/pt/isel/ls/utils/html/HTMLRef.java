package pt.isel.ls.utils.html;


public class HTMLRef extends HTMLElement {

    private final String url;


    public HTMLRef(String ref) {
        super(HTMLTypes.HREF);
        url = ref;

    }


    @Override
    protected void addTabs() {

    }

    @Override
    public String getName() {

        return "<a href=" + url + ">%s</a>";
    }

    public String getUrl() {
        return url;
    }

    public String With(String v) {
        return "<a href=" + url + ">" + v + "</a>";
    }
}
