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


public class Teacher extends Entity {
    public static final String CLASS_REF = "/courses/%s/classes/%s/%s";
    private static final String[] tableHeaders = {EMAIL, NAME, NUMBER, COURSE_ACR};
    public static final String TEACHER_REF = "/teachers/%s";

    private final int number;
    private final String email;
    private final String name;
    private List<Klass> classes;
    private boolean specfic;
    private boolean haveTaught;

    @Override
    public String toString() {
        return "Teacher{" +
                "number=" + number +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", classes=" + classes +
                '}';
    }

    public Teacher(int number, String email, String name) {
        super(tableHeaders);
        this.number = number;
        this.email = email;
        this.name = name;
    }

    public Teacher(String number, String email, String name) {
        super(tableHeaders);
        this.number = Integer.parseInt(number);
        this.email = email;
        this.name = name;
        specfic = false;

    }


    private Teacher(String number, String email, String name, UniqueTableDto assoc) {
        this(Integer.parseInt(number), email, name);
        specfic = true;
        //creating classes
        classes = new LinkedList<>();
        for (int i = 0; i < assoc.getRowNumber(); i++) {
            classes.add(Klass.of(assoc.getRowAt(i)));
        }
        haveTaught = classes.size() != 0;

    }


    public static String[] getHeaders() {
        return tableHeaders;
    }

    private static Teacher of(String number, String email, String name) {

        return new Teacher(number, email, name);
    }

    public static Teacher of(UniqueTableDto.SQLRow row) {

        return new Teacher(
                row.getColumnValue("number"),
                row.getColumnValue("email"),
                row.getColumnValue("name")
        );
    }

    public static Stream<Entity> creteTeacherStream(DTO table) {

        UniqueTableDto studentDto = (UniqueTableDto) table;
        List<Entity> teachers = new LinkedList<>();
        for (int i = 0; i < studentDto.getRowNumber(); i++) {
            teachers.add(of(studentDto.getRowAt(i)));
        }
        return teachers.stream();
    }
  public static Stream<Entity> creteTeacherForClassesStream(DTO table) {

        UniqueTableDto studentDto = (UniqueTableDto) table;
        List<Entity> teachers = new LinkedList<>();
        for (int i = 0; i < studentDto.getRowNumber(); i++) {
            teachers.add(ofFromClasses(studentDto.getRowAt(i)));
        }
        return teachers.stream();
    }

    private static Entity ofFromClasses(UniqueTableDto.SQLRow row) {
        return new Teacher(
                row.getColumnValue("teachernum"),
                row.getColumnValue("email"),
                row.getColumnValue("name")
        );
    }

    public static List<Teacher> createTeacher(HashMap<String, UniqueTableDto> tables) {
        UniqueTableDto teacherTable = tables.get("main");
        UniqueTableDto assocTable = tables.get("assoc");
        List<Teacher> teachers = new LinkedList<>();
        for (int i = 0; i < teacherTable.getRowNumber(); i++) {
            teachers.add(ofWithClasses(teacherTable.getRowAt(i), assocTable));
        }
        return teachers;
    }

    private static Teacher ofWithClasses(UniqueTableDto.SQLRow teacher, UniqueTableDto assoc) {
        //teacher info
        String name = teacher.getColumnValue("name");
        String email = teacher.getColumnValue("email");
        String number = teacher.getColumnValue("number");
        //assoc info process
        return new Teacher(number, email, name, assoc);
    }

    public int getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Generates an html row with the correspondent headers, depending if the print is generic or specif;
     *
     * @return an html row of headers.
     */
    @Override
    public HTMLElement getHtmlElement() {

        return specfic ?
                printSpecificTeacher() : printGenericTeacher();
    }

    /**
     * Generates an html row with the correspondent data, depending if the print is generic or specif;
     *
     * @return an html row of data.
     */
    @Override
    public HTMLTr<String> getHTMLHeaders() {
        return specfic ?
                super.getHTMLHeaders() : genericHeaders();
    }

    /**
     * Creates an html row with the basic headers of a teacher.
     *
     * @return html row wit basic teacher header.
     */
    private HTMLTr<String> genericHeaders() {
        HTMLTr<String> row = new HTMLTr<>();
        row
                .addHeader("Email")
                .addHeader("Name")
                .addHeader("Number");
        return row;
    }

    private HTMLTr<String> printGenericTeacher() {
        HTMLTr row = new HTMLTr<>()
                .addData(email)
                .addData(name)
                .addData(String.valueOf(number), format(TEACHER_REF, number));
        return row;
    }

    private HTMLTr<String> printSpecificTeacher() {
        HTMLTr row = new HTMLTr<>()
                .addData(email)
                .addData(name)
                .addData(String.valueOf(number));

        if (haveTaught) {
            putClassesHTML(row);
        }
        return row;
    }

    private void putClassesHTML(HTMLTr row) {
        HTMLUL<String> line = createClassesList();

        row.addData(line);
    }

    private HTMLUL<String> createClassesList() {
        HTMLUL<String> line = new HTMLUL<>();
        classes.forEach(klass -> {
            String courseAcronym = klass.getCourSeAcronym();
            String semester = klass.getSemester();
            String id = klass.getId();
            String ref = format(CLASS_REF, courseAcronym, semester, id);
            HTMLListItem li = new HTMLListItem<>(" " + courseAcronym + semester + " " + id, ref.toLowerCase());
            line.addChild(li);
        });
        return line;
    }

    @Override
    public String getJson() {
        return "{\"Email\" : "+"\""+email+"\","+"\"Name\" : "+"\""+name
                +"\","+"\"Number\" : "+"\""+number+"\"}";
    }

    @Override
    public String getPlaintText() {
        return toString();
    }

    public String getName() {
        return name;
    }

    public List<Klass> getClasses() {
        return classes;
    }

    public static Stream<Entity> createOneTeacher(DTO dto) {
        MultiTableDto table = (MultiTableDto) dto;
        return createTeacher(table.getMap()).stream();
    }
}
