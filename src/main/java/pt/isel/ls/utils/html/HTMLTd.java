package pt.isel.ls.utils.html;


public class HTMLTd<T> extends HTMLCell<T> {

    public HTMLTd(T cellValue, HTMLRef htmlRef) {
        super(cellValue, HTMLTypes.TD, htmlRef);
    }

    public HTMLTd(T value) {
        super(value, HTMLTypes.TD);
    }
}
