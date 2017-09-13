package pt.isel.ls.utils.html;


public class HTMLTable extends AbstractNonLeafElement<HTMLTr, HTMLTable> {

    public HTMLTable() {
        super(HTMLTypes.TABLE);
    }

    @Override
    public HTMLTable addChild(HTMLTr child) {
        list.add(child);
        return this;
    }


    public <T extends String> HTMLTr addRow() {
        return new HTMLTr<T>();
    }
}
