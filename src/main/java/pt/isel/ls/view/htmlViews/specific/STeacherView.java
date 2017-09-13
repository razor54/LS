package pt.isel.ls.view.htmlViews.specific;

import pt.isel.ls.model.data.entities.Klass;
import pt.isel.ls.model.data.entities.Student;
import pt.isel.ls.model.data.entities.Teacher;
import pt.isel.ls.utils.html.HTMLListItem;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.utils.html.HTMLUL;
import pt.isel.ls.view.htmlViews.general.TeacherView;

import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;
import static pt.isel.ls.model.data.entities.Programme.COURSE_REF;
import static pt.isel.ls.model.data.entities.Student.CLASS_REF;


public class STeacherView extends TeacherView {

    private static final String htmlFile = "public/views/SpecificTeacher.html";

    public STeacherView() {
        super(htmlFile);
    }

    @Override
    public String getEntityHtml(Teacher t) {
        return addClassReference(contructGenericRow(t), t).getName();
    }


    private HTMLTr<String> addClassReference(HTMLTr<String> row, Teacher s) {
        HTMLUL<String> classes = new HTMLUL<>();
        HTMLUL<String> courses = new HTMLUL<>();
        List<String> acronyms = setClassReferences(classes, s);
        setCourseReferences(courses, acronyms);
        return row
                .addData(classes.getName())
                .addData(courses.getName());
    }

    private void setCourseReferences(HTMLUL<String> courses, List<String> acronyms) {
        acronyms
                .stream()
                .distinct()
                .forEach(acr -> {
                    courses.addChild(new HTMLListItem<>(acr, format(COURSE_REF, acr)));
                });

    }

    private List<String> setClassReferences(HTMLUL<String> line, Teacher t) {

        List<String> acronyms = new LinkedList<>();

        t.getClasses().forEach((Klass klass) -> {
            StringBuilder classBuilder = new StringBuilder();
            String courSeAcronym = klass.getCourSeAcronym();
            acronyms.add(courSeAcronym);
            String semester = klass.getSemester();
            String id = klass.getId();
            classBuilder
                    .append(courSeAcronym)
                    .append(" ")
                    .append(semester)
                    .append(" ")
                    .append(id);
            String ref = format(CLASS_REF, courSeAcronym, semester, id);
            HTMLListItem li = new HTMLListItem<>(classBuilder.toString(),ref);
            line.addChild(li);
        });
        return acronyms;
    }
}
