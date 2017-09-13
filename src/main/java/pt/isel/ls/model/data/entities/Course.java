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
import static java.lang.String.valueOf;
import static pt.isel.ls.utils.ColumnNames.*;


public class Course extends Entity {
    public static final String ACR_CLASSES = "/courses/%s/classes";
    public static final String COORDINATOR_REF = "/teachers/%s";
    public static final String PROGRAM_REF = "/programmes/%s";
    private static final String[] tableHeaders = {MANDATORY, NAME, ACRONYM, COORDINATOR};
    private final String courceAcronym;
    private final int coordinatorID;
    private final String name;
    private final boolean mandatory;
    private final List<String> semesters;
    private final List<String> programmes;
    private boolean specific;

    //**********************************************************************************//
    //*****************************  CLASS CONSTRUCTORS  *******************************//
    //**********************************************************************************//
    private Course(String courceAcronym, int teacherNum, boolean mandatoy, String name) {

        super(tableHeaders);
        this.courceAcronym = courceAcronym;
        this.mandatory = mandatoy;
        coordinatorID = teacherNum;
        this.name = name;
        specific = false;
        this.semesters = new LinkedList<>();
        this.programmes = new LinkedList<>();
    }

    public Course(String courceAcronym, String coordinator, String mandatory, String name) {
        this(courceAcronym, Integer.parseInt(coordinator), Boolean.valueOf(mandatory), name);
    }


    public Course(String acronym, String coordinator, String mandatory, String name, String[] currularSems, String[] programmes) {
        super(tableHeaders);
        this.courceAcronym = acronym;
        this.mandatory = Boolean.valueOf(mandatory);
        coordinatorID = Integer.parseInt(coordinator);
        this.name = name;
        specific = true;
        this.semesters = (Arrays.asList(currularSems));
        this.programmes = (Arrays.asList(programmes));
    }

    public static String[] getHeaders() {
        return tableHeaders;
    }

    //**********************************************************************************/

    public static Course of(UniqueTableDto.SQLRow row, UniqueTableDto assocTable, UniqueTableDto programTable) {
        String[] currularSems = assocTable.getColumn("curricularsem");
        String[] programmes = programTable.getColumn("pid");

        return new Course(
                row.getColumnValue("acronym"),
                row.getColumnValue("coordinator"),
                row.getColumnValue("mandatory"),
                row.getColumnValue("name"),
                currularSems,
                programmes

        );
    }

    public static Course of(UniqueTableDto.SQLRow row) {
        return new Course(
                row.getColumnValue("acronym"),
                row.getColumnValue("coordinator"),
                row.getColumnValue("mandatory"),
                row.getColumnValue("name")
        );
    }

    public static Stream<Entity> creteateCourseStream(DTO row) {
        UniqueTableDto lines = (UniqueTableDto) row;
        List<Entity> courses = new LinkedList<>();
        for (int i = 0; i < lines.getRowNumber(); i++) {
            courses.add(of(lines.getRowAt(i)));
        }
        return courses.stream();
    }

    /**
     * Creates a course from an hashmap of string into UniqueTable
     * @param tables  map with tables
     * @return a new course with the information of the tables
     */
    public static List<Course> createCourse(HashMap<String, UniqueTableDto> tables) {
        UniqueTableDto courseTable = tables.get("main");
        UniqueTableDto assocTable = tables.get("sems");
        UniqueTableDto programmesTable = tables.get("programmes");

        List<Course> courses = new LinkedList<>();
        courses.add(of(courseTable.getRowAt(0), assocTable, programmesTable));

        return courses;
    }

    //**********************************************************************************/
    //*******************************  CLASS BODY **************************************/
    @Override
    public HTMLElement getHtmlElement() {
        // basic info
        HTMLTr<String> line = new HTMLTr<>();
        line
                .addData(valueOf(mandatory))
                .addData(name)
                .addData(courceAcronym)
                .addData(valueOf(coordinatorID), format(COORDINATOR_REF, coordinatorID));
        // extra info
        return specific ? addSemestersAndProgrammes(line) : line;
    }

    private HTMLTr<String> addSemestersAndProgrammes(HTMLTr<String> line) {
        HTMLUL<String> t_list = new HTMLUL();

        semesters.forEach(sem -> {
            t_list.addChild(new HTMLListItem<>("--" + sem + " \n"));
        });

        HTMLUL p_list = new HTMLUL();
        programmes.forEach(program -> {
            p_list.addChild(new HTMLListItem<>("--" + program + " \n", format(PROGRAM_REF, program)));
        });

        return line.addData(t_list.getName());

    }

    @Override
    public String toString() {
        return "Course{" +
                "courceAcronym='" + courceAcronym + '\'' +
                ", coordinatorID=" + coordinatorID +
                ", name='" + name + '\'' +
                ", mandatory=" + mandatory +
                ", semesters=" + semesters +
                ", programmes=" + programmes +
                '}';
    }

    @Override
    public String getJson() {
        return "{\"Course\" : " + "\"" + courceAcronym + "\"," + "\"coordinator\" : " + "\"" + coordinatorID
                + "\"," + "\"Name\" : " + "\"" + name + "\"" + "\"Programme\" : " +
                "\"" + programmes + "\"" + "\"Semesters\" : " + "\"" + semesters + "\"" + "}";

    }

    @Override
    public String getPlaintText() {
        return toString();
    }

    @Override
    public HTMLTr<String> getHTMLHeaders() {
        return super.getHTMLHeaders();
    }

    private HTMLTr<String> addAditionalHeaders(HTMLTr<String> headers) {
        return headers
                .addHeader(SEM_REPRESENTATION)
                .addHeader(PROGRAMME_ID);
    }

    public String getCourceAcronym() {
        return courceAcronym;
    }

    public int getCoordinatorID() {
        return coordinatorID;
    }

    public String getName() {
        return name;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public List<String> getSemesters() {
        return semesters;
    }

    public List<String> getProgrammes() {
        return programmes;
    }

    public boolean getMandatory() {
        return mandatory;
    }

    public static Stream<Entity> createOneCourse(DTO dto) {
        MultiTableDto table = (MultiTableDto) dto;
        return createCourse(table.getMap()).stream();
    }
}
