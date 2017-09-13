package pt.isel.ls.utils.html;


public enum HTMLTypes {
    BODY,
    HEAD,
    H,
    HTML,
    HREF,
    TABLE,
    TITLE,
    TD,
    TH,
    TR, IL, UL, LINK, INPUT, FORM;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
