package pt.isel.ls.utils.html;


public class HTMLListItem<T> extends HTMLElement {
    private final T cellValue;

    public HTMLListItem(T cellValue, String ref) {
        super(HTMLTypes.IL, new HTMLRef(ref));
        this.cellValue = cellValue;
        //this.withAttr("href", ref);
    }

    public HTMLListItem(T value) {
        super(HTMLTypes.IL);
        this.cellValue = value;
    }

    @Override
    protected void addTabs() {

    }

    @Override
    public String getName() {
        String s = "<" + type + writeAttrs() + ">" + getHref(cellValue.toString()) + "</" + type + ">";
        return s;

    }

    ;


}