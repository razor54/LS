package pt.isel.ls.view.htmlViews.specific;


import pt.isel.ls.model.data.entities.Klass;
import pt.isel.ls.model.data.entities.Student;
import pt.isel.ls.utils.html.HTMLListItem;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.utils.html.HTMLUL;
import pt.isel.ls.view.htmlViews.general.StudentView;
import static java.lang.String.format;
import static pt.isel.ls.model.data.entities.Student.CLASS_REF;


public class SStudentView extends StudentView {

    private static final String htmlFile = "public/views/SpecificStudent.html";

    public SStudentView() {
        super(htmlFile);
    }

    @Override
    public String getEntityHtml(Student c) {
        return addClassReference(contructGenericRow(c), c).getName();
    }


    private HTMLTr<String> addClassReference(HTMLTr<String> row, Student s) {
        HTMLUL<String> line = new HTMLUL<>();
        setClassReferences(line, s);
        return row.addData(line.getName());
    }

    private void setClassReferences(HTMLUL<String> line, Student s) {

        s.getClasses().forEach((Klass klass) -> {
            StringBuilder classBuilder = new StringBuilder();
            String courSeAcronym = klass.getCourSeAcronym();
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
    }

}

