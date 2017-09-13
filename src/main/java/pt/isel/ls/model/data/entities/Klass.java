package pt.isel.ls.model.data.entities;

import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.utils.html.HTMLElement;
import pt.isel.ls.utils.html.HTMLListItem;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.utils.html.HTMLUL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static pt.isel.ls.utils.ColumnNames.*;


public class Klass extends Entity {
    public static final String CLASS_REF = "/courses/%s/classes/%s/%s";
    private static final String[] tableHeaders = {COURSE_ACR, SEM_REPRESENTATION, CLASS_ID};
    private static final String[] pageHeaders = {"Coordinator"};
    private static final String TEACHER_LINK = "teachers/%s";
    private final String courceAcronym;
    private final String semester;
    private final String id;
    private final List<Teacher> teachers;
    private final List<Student> students;
    private final boolean specific;

    //**********************************************************************************/
    //*****************************  CLASS CONSTRUCTORS  *******************************/
    //**********************************************************************************/
    private Klass(String courceAcronym, String semester, String id) {

        super(tableHeaders);
        this.courceAcronym = courceAcronym;
        this.semester = semester;
        this.id = id;
        specific = false;
        teachers = new LinkedList<>();
        students = new LinkedList<>();
    }

    public Klass(String courseACR, String semRepresentation, String cID, List<Teacher> teachers, List<Student> students ) {
        super(tableHeaders);
        this.courceAcronym = courseACR;
        this.semester = semRepresentation;
        this.id = cID;
        specific = true;
        this.teachers = teachers;
        this.students = students;
    }

    public static String[] getHeaders() {
        return tableHeaders;
    }

    public static Klass createClass(String courceAcronym, String semester, String id) {
        return new Klass(courceAcronym, semester, id);
    }

    public static Klass of(UniqueTableDto.SQLRow row) {
        return new Klass(
                row.getColumnValue("classcourseacr"),
                row.getColumnValue("classcoursesem"),
                row.getColumnValue("classcid")
        );
    }
    public static Klass ofForCoursesClass(UniqueTableDto.SQLRow row) {
        return new Klass(
                row.getColumnValue("courseacr"),
                row.getColumnValue("semrepresentation"),
                row.getColumnValue("cid")
        );
    }
    public static Stream<Entity> createClass(DTO row) {
        UniqueTableDto lines = (UniqueTableDto) row;
        List<Entity> classes = new LinkedList<>();
        for (int i = 0; i < lines.getRowNumber(); i++) {
            classes.add(of(lines.getRowAt(i)));
        }
        return classes.stream();
    }
    public static Stream<Entity> createClassForCourses(DTO row) {
        UniqueTableDto lines = (UniqueTableDto) row;
        List<Entity> classes = new LinkedList<>();
        for (int i = 0; i < lines.getRowNumber(); i++) {
            classes.add(ofForCoursesClass(lines.getRowAt(i)));
        }
        return classes.stream();
    }


    public static Entity createClass(HashMap<String, UniqueTableDto> tables) {
        UniqueTableDto classTable = tables.get("main");
        UniqueTableDto teachers = tables.get("teachers");
        UniqueTableDto students = tables.get("students");

        return createClass(classTable.getRowAt(0), teachers,students);
    }

    private static Klass createClass(UniqueTableDto.SQLRow row, UniqueTableDto teachers,UniqueTableDto students) {
        List<Teacher> ts = _createTeachers(teachers);
        List<Student> sts = _createStudents(students);
        return new Klass(
                row.getColumnValue("courseacr"),
                row.getColumnValue("semrepresentation"),
                row.getColumnValue("cid"),
                ts,
                sts
        );


    }

    public static Stream<Entity> createOneClass(DTO dto){
        MultiTableDto elem = (MultiTableDto) dto;
        return Stream.of( createClass(elem.getMap()));
    }
    private static List<Student> _createStudents(UniqueTableDto studentsDto) {
        List<Student> sts = new LinkedList<>();

        for (int i = 0; i < studentsDto.getRowNumber(); i++) {
            UniqueTableDto.SQLRow rowAt = studentsDto.getRowAt(i);
            sts.add(new Student(
                    rowAt.getColumnValue("email"),
                    rowAt.getColumnValue("programmeid"),
                    rowAt.getColumnValue("number"),
                    rowAt.getColumnValue("name")


            ));
        }
        return sts;
    }

    private static List<Teacher> _createTeachers(UniqueTableDto teachersDto) {
        List<Teacher> ts = new LinkedList<>();
       
        for (int i = 0; i < teachersDto.getRowNumber(); i++) {
            UniqueTableDto.SQLRow rowAt = teachersDto.getRowAt(i);
            ts.add(new Teacher(
                    rowAt.getColumnValue("number"),
                    rowAt.getColumnValue("email"),
                    rowAt.getColumnValue("name")

            ));
        }
        return ts;
    }

    public String getCourSeAcronym() {
        return courceAcronym;
    }

    //**********************************************************************************/
    //*******************************  CLASS BODY **************************************/
    //**********************************************************************************/

    public String getSemester() {
        return semester;
    }

    public String getId() {
        return id;
    }

    private HTMLTr<String> printGenericClass() {
        HTMLTr<String> line = new HTMLTr<>();
        return line
                .addData(courceAcronym,"/courses/"+courceAcronym)
                .addData(semester)
                .addData(id, String.format(CLASS_REF, courceAcronym, semester, id));

    }

    private HTMLTr<String> printSpecifcClass() {
        HTMLTr<String> line = new HTMLTr<>();
        line
                .addData(courceAcronym,"/courses/"+courceAcronym)
                .addData(semester)
                .addData(id);

        HTMLUL<String> t_list = new HTMLUL<>();
        //creating teacher list
        teachers.forEach(teacher -> {
            String t_ref = format(TEACHER_LINK, teacher.getNumber());
            t_list.addChild(new HTMLListItem<>(teacher.getName(), t_ref));
        });
        return line;
    }

    @Override
    public HTMLElement getHtmlElement() {
        return specific ?
                printSpecifcClass() : printGenericClass();
    }

    @Override
    public String getJson() {
        return "{\"Course\" : " + "\"" + courceAcronym + "\"," + "\"Semester\" : " + "\"" + semester
                + "\"," + "\"Id\" : " + "\"" + id + "\"" + "\"Teachers\" : " +
                "\"" + teachers +"\"" + "}";

    }

    @Override
    public String getPlaintText() {
        return toString();
    }

    @Override
    public HTMLTr getHTMLHeaders() {
        HTMLTr<String> row = new HTMLTr<>();
        for (String tableHeader : tableHeaders) {
            row.addHeader(tableHeader);
        }
        return row;
    }

    public String getCourceAcronym() {
        return courceAcronym;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    @Override
    public String toString() {
        return "Klass{" +
                "courceAcronym='" + courceAcronym + '\'' +
                ", semester='" + semester + '\'' +
                ", id='" + id + '\'' +
                ", teachers=" + teachers +
                '}';
    }

    public List<Student> getStudents() {
        return students;
    }
}
