package pt.isel.ls.view.htmlViews.specific;

import pt.isel.ls.model.data.entities.Course;
import pt.isel.ls.utils.html.HTMLListItem;
import pt.isel.ls.utils.html.HTMLRef;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.utils.html.HTMLUL;
import pt.isel.ls.view.htmlViews.general.CourseView;

import java.util.stream.Stream;

import static java.lang.String.format;
import static pt.isel.ls.model.data.entities.Course.PROGRAM_REF;


public class SCourseView extends CourseView {
    private static final String htmlFile = "public/views/SpecificCourse.html";

    public SCourseView() {
        super(htmlFile);
    }

    @Override
    public String getEntityHtml(Course c) {
        return addSemestersAndProgrammes(contructGenericRow(c),c).getName();
    }

    @Override
    public String getHtml(Stream<Course> entities) {
        if(infoTable==null)return super.getHtml(entities);
        String path =infoTable.getPath();
        return String.format(readHtmlFromFIle(filePath),new HTMLRef(path+"/classes").With("CLASSES"),
                _formatter.formatHtml(translateStream(entities),1)
                ,buildPaging(infoTable));
    }

    private HTMLTr<String> addSemestersAndProgrammes(HTMLTr<String> line, Course c) {
        HTMLUL<String> t_list = new HTMLUL();

        c.getSemesters().forEach(sem -> {
            t_list.addChild(new HTMLListItem<>("--" + sem+ " "));
        });

        HTMLUL p_list = new HTMLUL();
        c.getProgrammes().forEach(program -> {
            p_list.addChild(new HTMLListItem<>("--" + program +" ", format(PROGRAM_REF, program)));
        });

        return line
                .addData(t_list.getName())
                .addData(p_list.getName());

    }
}
