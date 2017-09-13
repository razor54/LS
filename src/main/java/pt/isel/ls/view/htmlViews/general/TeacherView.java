package pt.isel.ls.view.htmlViews.general;

import pt.isel.ls.model.data.entities.Teacher;
import pt.isel.ls.utils.Formatter;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.view.htmlViews.View;

import java.text.Format;
import java.util.stream.Stream;

import static java.lang.String.valueOf;


public class TeacherView extends View<Teacher> {

    private static final String htmlFile = "public/views/Teachers.html";

    private String filePath;
    protected TeacherView(String filePath) {
        this.filePath = filePath;
    }

    public TeacherView() {
        this.filePath = htmlFile;
    }

    @Override
    public String getHtml(Stream<Teacher> entities) {
        return  String.format(readHtmlFromFIle(filePath),
                _formatter.formatHtml(translateStream(entities),1)
                , buildPaging(infoTable));
    }

    @Override
    public String getEntityHtml(Teacher t) {
        HTMLTr<String> line = contructGenericRow(t);
        // extra info
        return line.getName();
    }

    protected HTMLTr<String> contructGenericRow(Teacher t) {
        HTMLTr<String> row = new HTMLTr<>();
        int number = t.getNumber();
        return row
                .addData(t.getEmail(),"/users")
                .addData(t.getName())
                .addData(valueOf(number), "/teachers/" + number);
    }
}
