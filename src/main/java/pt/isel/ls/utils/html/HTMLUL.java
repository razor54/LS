package pt.isel.ls.utils.html;

/**
 * Created by Andre on 27-May-17.
 */
public class HTMLUL<T>
        extends AbstractNonLeafElement<HTMLListItem, HTMLUL<HTMLListItem<T>>> {


    public HTMLUL() {
        super(HTMLTypes.UL);
    }

    @Override
    public HTMLUL addChild(HTMLListItem child) {
        list.add(child);
        return this;
    }

    @Override
    public String getName() {
        return formName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
