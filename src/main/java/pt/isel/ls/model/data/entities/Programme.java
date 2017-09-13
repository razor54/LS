package pt.isel.ls.model.data.entities;


import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.utils.html.HTMLElement;
import pt.isel.ls.utils.html.HTMLListItem;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.utils.html.HTMLUL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static pt.isel.ls.utils.ColumnNames.*;


public class Programme extends Entity {
    public static final String COURSE_REF = "/courses/%s";
    private static final String[] tableHeaders = {NAME, PROGRAMME_ID, LENGTH};
    private final String programID;
    private final int length;
    private final String name;
    private final boolean specific;
    private List<String> courses;

    private Programme(String programID, int length, String name) {
        super(tableHeaders);
        this.programID = programID;
        this.length = length;
        this.name = name;
        specific = false;
    }

    public Programme(String pid, String length, String name, String[] courses) {
        super(tableHeaders);
        this.programID = pid;
        this.length = Integer.parseInt(length);
        this.name = name;
        this.courses = Arrays.asList(courses);
        specific = true;
    }

    public Programme(String pid, String length, String name) {
        this(pid, Integer.parseInt(length), name);
    }

    public static String[] getHeaders() {
        return tableHeaders;
    }


    public static List<Programme> createProgramme(HashMap<String, UniqueTableDto> tables) {
        UniqueTableDto programeTable = tables.get("main");
        UniqueTableDto programmeAssoc = tables.get("courses");

        List<Programme> list = new LinkedList<>();
        list.add(of(programeTable.getRowAt(0), programmeAssoc));

        return list;
    }

    private static Programme of(UniqueTableDto.SQLRow programme, UniqueTableDto programmeAssoc) {
        String[] courses = programmeAssoc.getColumn("courseid");


        return new Programme(
                programme.getColumnValue("pid"),
                programme.getColumnValue("length"),
                programme.getColumnValue("name"),
                courses
        );
    }

    public static Programme of(UniqueTableDto.SQLRow programme) {


        return new Programme(
                programme.getColumnValue("pid"),
                programme.getColumnValue("length"),
                programme.getColumnValue("name")
        );
    }

    @Override
    public HTMLElement getHtmlElement() {
        HTMLTr<String> row = new HTMLTr<>();
        row
                .addData(name)
                .addData(programID)
                .addData(String.valueOf(length));

        HTMLUL<String> line = new HTMLUL<>();


        if (courses != null) {
            courses.forEach(course -> {
                HTMLListItem li = new HTMLListItem<>(course, format(COURSE_REF, course));
                line.addChild(li);
            });
            row.addData(line.getName());
        }

        return row;
    }

    @Override
    public String getJson() {
        return "{\"Programme Id\" : " + "\"" + programID + "\"," + "\"Name\" : " + "\"" + name
                + "\"," + "\"Length\" : " + "\"" + length + "\"" + "}";

    }

    @Override
    public String getPlaintText() {
        return toString();
    }

    @Override
    public HTMLTr<String> getHTMLHeaders() {
        HTMLTr<String> row = super.getHTMLHeaders();
        row.addHeader("Courses");
        return row;
    }

    public List<String> getCourses() {
        return courses;
    }

    public String getProgramID() {

        return programID;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Programme{" +
                "programID='" + programID + '\'' +
                ", length=" + length +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }

    public static Stream<Entity> createProgrammeStream(DTO row) {
        UniqueTableDto lines = (UniqueTableDto) row;
        List<Entity> programmes = new LinkedList<>();
        for (int i = 0; i < lines.getRowNumber(); i++) {
            programmes.add(of(lines.getRowAt(i)));
        }
        return programmes.stream();
    }

    public static Stream<Entity> createOneProgramme(DTO dto) {
        MultiTableDto table = (MultiTableDto) dto;
        return createProgramme(table.getMap()).stream();
    }
}
