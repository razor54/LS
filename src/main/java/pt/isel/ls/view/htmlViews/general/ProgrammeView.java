package pt.isel.ls.view.htmlViews.general;


import pt.isel.ls.model.data.entities.Programme;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.view.htmlViews.View;

import java.util.stream.Stream;

import static java.lang.String.format;
import static pt.isel.ls.model.data.entities.Course.PROGRAM_REF;


public class ProgrammeView extends View<Programme> {
    private static final String htmlFile = "public/views/Programmes.html";

    private String filePath;

    protected ProgrammeView(String filePath) {
        this.filePath = filePath;
    }

    public ProgrammeView() {
        this.filePath = htmlFile;
    }

    @Override
    public String getHtml(Stream<Programme> entities) {

        return String.format(readHtmlFromFIle(filePath),
                _formatter.formatHtml(translateStream(entities), 1)
                , buildPaging(infoTable));
    }

    @Override
    public String getEntityHtml(Programme p) {
        HTMLTr<String> line = contructGenericRow(p);
        // extra info
        return line.getName();
    }


    protected final HTMLTr<String> contructGenericRow(Programme p) {
        HTMLTr<String> row = new HTMLTr<>();
        String programID = p.getProgramID();
        return row
                .addData(p.getName())
                .addData(programID, format(PROGRAM_REF, programID))
                .addData(String.valueOf(p.getLength()));
    }
}
