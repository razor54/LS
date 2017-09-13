package pt.isel.ls.utils.html;

import pt.isel.ls.utils.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leonardo Freire on 06/06/2017.
 */
public class HTMLForm extends AbstractNonLeafElement<HTMLInput, HTMLForm> {

    private List<Pair<String, String>> formContent = new LinkedList<>();

    public HTMLForm(Pair<String, String>[] fields) {
        super(HTMLTypes.FORM);
        formContent.addAll(Arrays.asList(fields));
    }

    @Override
    public String getName() {
        StringBuilder res = new StringBuilder();
        for (Pair pair : formContent) {
            res.append(pair);
        }
        return "<" + type + writeAttrs() + ">" + res + "</" + type + ">";
    }

    @Override
    public HTMLForm addChild(HTMLInput child) {
        return null;
    }
}
