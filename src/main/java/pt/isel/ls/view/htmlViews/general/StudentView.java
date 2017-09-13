package pt.isel.ls.view.htmlViews.general;


import pt.isel.ls.model.data.entities.Student;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.view.htmlViews.View;

import java.util.stream.Stream;

import static java.lang.String.valueOf;


public class StudentView extends View<Student> {

    private static final String htmlFile = "public/views/Students.html";

    private String filePath;

    protected StudentView(String filePath) {
        this.filePath = filePath;
    }

    public StudentView() {
        this.filePath = htmlFile;
    }

    @Override
    public String getHtml(Stream<Student> entities) {
        return String.format(readHtmlFromFIle(filePath),
                _formatter.formatHtml(translateStream(entities),1)
                , buildPaging(infoTable));
    }

    @Override
    public String getEntityHtml(Student s) {
        HTMLTr<String> line = contructGenericRow(s);
        // extra info
        return line.getName();
    }

    protected HTMLTr<String> contructGenericRow(Student s) {
        HTMLTr<String> row = new HTMLTr<>();
        int number = s.getNumber();
        return row
                .addData(s.getEmail(),"/users")
                .addData(s.getName())
                .addData(s.getPid(),"/programmes/"+s.getPid())
                .addData(valueOf(number), "/students/" + number);
    }

}
