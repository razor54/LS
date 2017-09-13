package pt.isel.ls.utils.html;


public class HTMLTr<T> extends AbstractNonLeafElement<HTMLCell<T>, HTMLTr> {

    public HTMLTr() {
        super(HTMLTypes.TR);
    }

    public HTMLTr<T> addHeader(T value, String reference) {
        return addChild(new HTMLTh<>(value, new HTMLRef(reference)));
    }

    public HTMLTr<T> addHeader(T value) {
        return addChild(new HTMLTh<T>(value));
    }

    public HTMLTr<T> addData(T value) {
        return addChild(new HTMLTd<>(value));

    }

    public HTMLTr<T> addData(T value, String href) {
        return addChild(new HTMLTd<>(value, new HTMLRef(href)));
    }

    @Override
    public String getName() {
        return formName();
    }


    @Override
    public HTMLTr<T> addChild(HTMLCell<T> child) {
        list.add(child);
        return this;
    }


}
