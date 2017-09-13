package pt.isel.ls.view.htmlViews.specific;

import pt.isel.ls.model.data.entities.Programme;
import pt.isel.ls.utils.html.HTMLListItem;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.utils.html.HTMLUL;
import pt.isel.ls.view.htmlViews.general.ProgrammeView;

import java.util.stream.Stream;

import static java.lang.String.format;
import static pt.isel.ls.model.data.entities.Programme.COURSE_REF;


public class SProgrammeView extends ProgrammeView {

    private static final String htmlFile = "public/views/SpecificProgramme.html";

    public SProgrammeView() {
        super(htmlFile);
    }

    @Override
    public String getEntityHtml(Programme p) {
        return addCoursesTo(contructGenericRow(p),p).getName();
    }


    private HTMLTr<String> addCoursesTo(HTMLTr<String> row, Programme c) {
        HTMLUL<String> line = new HTMLUL<>();

        c.getCourses().forEach(course -> {
            HTMLListItem li = new HTMLListItem<>(course, format(COURSE_REF, course));
            line.addChild(li);
        });
        return row.addData(line.getName());
    }

    @Override
    public String getHtml(Stream<Programme> entities) {
        return  String.format(
                readHtmlFromFIle(htmlFile),
                translateStream(entities),
                infoTable.getPath().split("/")[2]);
    }

}
