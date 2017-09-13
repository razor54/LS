package pt.isel.ls.utils.html;


public class HTMLHeading extends HTMLElement {
    private final String cellValue;
    private final int headType;

    protected HTMLHeading(String value, int headType) {
        super(HTMLTypes.H);
        cellValue = value;
        this.headType = headType < 0 || headType > 6 ? 1 : headType;

    }

    protected HTMLHeading(String href, String value, int headType) {
        super(HTMLTypes.H, new HTMLRef(href));
        cellValue = value;
        this.headType = headType < 0 || headType > 6 ? 1 : headType;
    }

    @Override
    protected void addTabs() {

    }

    @Override
    public String getName() {
        return "<" + type + headType + ">" + getHref(cellValue) + "</" + type + headType + ">";
    }

    public static HTMLHeading Of(String href, String value, int headType) {
        return new HTMLHeading(href, value, headType);
    }

    public static HTMLHeading Of(String value, int headType) {
        return new HTMLHeading(value, headType);
    }


}
