package pt.isel.ls.view.htmlViews.general;

import pt.isel.ls.model.data.entities.Klass;
import pt.isel.ls.utils.Formatter;
import pt.isel.ls.utils.html.HTMLRef;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.view.htmlViews.View;

import java.util.stream.Stream;

import static pt.isel.ls.model.data.entities.Klass.CLASS_REF;


public class ClassView extends View<Klass> {
    private static final String htmlFile = "public/views/Classes.html";

    private String filePath;


    protected ClassView(String filePath) {
        this.filePath = filePath;
    }

    public ClassView() {
        this.filePath = htmlFile;
    }

    @Override
    public String getHtml(Stream<Klass> entities) {
        return String.format(readHtmlFromFIle(filePath),
                _formatter.formatHtml(translateStream(entities),1),
                buildPaging(infoTable));
    }

    @Override
    public String getPlain(Stream<Klass> entities) {
        StringBuilder res = new StringBuilder();
        entities.forEach(en -> res.append(en.getPlaintText()).append("\n"));
        return res.toString();
    }

    @Override
    public String getJson(Stream<Klass> entities) {
        StringBuilder res = new StringBuilder();
        entities.forEach(en -> res.append(en.getJson()).append("\n"));
        return res.toString();
    }

    public String getEntityHtml(Klass c) {
        // basic info
        return contructGenericRow(c).getName();
    }

    protected final HTMLTr<String> contructGenericRow(Klass c) {
        HTMLTr<String> line = new HTMLTr<>();
        String id = c.getId();
        return line
                .addData(c.getCourceAcronym(),"/courses/"+c.getCourceAcronym())  //back to classes from here
                .addData(c.getSemester())
                .addData(id, String.format(CLASS_REF, c.getCourceAcronym(), c.getSemester(), id).toLowerCase());
    }

}
