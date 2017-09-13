package pt.isel.ls.utils.html;


public abstract class HTMLCell<T> extends HTMLElement {
    private final T cellValue;

    public HTMLCell(T cellValue, HTMLTypes type, HTMLRef htmlRef) {
        super(type, htmlRef);
        this.cellValue = cellValue;
    }

    public HTMLCell(T value, HTMLTypes td) {
        super(td);
        this.cellValue = value;
    }

    @Override
    protected void addTabs() {

    }

    @Override
    public final String getName() {
        return "<" + type + ">" + getHref(cellValue.toString()) + "</" + type + ">";
    }


}