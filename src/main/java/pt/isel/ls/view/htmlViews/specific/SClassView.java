package pt.isel.ls.view.htmlViews.specific;

import pt.isel.ls.model.data.entities.Klass;
import pt.isel.ls.model.data.entities.Student;
import pt.isel.ls.model.data.entities.Teacher;
import pt.isel.ls.utils.html.*;
import pt.isel.ls.view.htmlViews.general.ClassView;

import java.util.List;
import java.util.stream.Stream;

import static pt.isel.ls.model.data.entities.Student.STUDENT_REF;
import static pt.isel.ls.model.data.entities.Teacher.TEACHER_REF;


public class SClassView extends ClassView {

    private static final String htmlFile = "public/views/SpecificClass.html";

    public SClassView() {
        super(htmlFile);
    }

    @Override
    public String getHtml(Stream<Klass> entities) {
        Klass first = entities.findFirst().get();

        return String.format(readHtmlFromFIle(htmlFile),
                first.getCourceAcronym(),
                _formatter.formatHtml(buildClass(first),1),
                infoTable.getPath(),
                _formatter.formatHtml(buildTeachers(first.getTeachers()),1),
                infoTable.getPath(),
                _formatter.formatHtml(buildStudents(first.getStudents()),1),
                infoTable.getPath(),
                infoTable.getPath());
    }

    private String buildClass(Klass klass) {
        return klass.getHtmlElement().getName();
    }

    protected String buildTeachers(List<Teacher> teachers) {
        HTMLTable table = new HTMLTable();
        teachers.forEach(e -> {
            table.addChild(new HTMLTr<String>().addData(e.getName(), String.format(TEACHER_REF, e.getNumber())));
        });
        return table.getName();
    }

    protected String buildStudents(List<Student> students) {
        HTMLTable table = new HTMLTable();
        students.forEach(e -> {
            table.addChild(new HTMLTr<String>().addData(e.getName(), String.format(STUDENT_REF, e.getNumber())));
        });
        return table.getName();
    }

    private HTMLTr<String> addTeachersAndStudents(HTMLTr<String> line, Klass klass) {
        HTMLUL<String> t_list = new HTMLUL();

        klass.getTeachers().forEach(sem -> {
            t_list.addChild(new HTMLListItem<>("--" + sem+ " "));
        });

        return line
                .addData(t_list.getName());
    }
}
