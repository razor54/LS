package pt.isel.ls.utils.html;


public class HTMLTh<T> extends HTMLCell<T> {

    public HTMLTh(T cellValue, HTMLRef htmlRef) {
        super(cellValue, HTMLTypes.TH, htmlRef);
    }

    public HTMLTh(T value) {
        super(value, HTMLTypes.TH);
    }

    @Override
    protected void addTabs() {

    }

}
