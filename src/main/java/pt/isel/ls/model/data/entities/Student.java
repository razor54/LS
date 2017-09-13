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
import static java.lang.String.valueOf;
import static pt.isel.ls.utils.ColumnNames.*;


public class Student extends Entity {
    public static final String CLASS_REF = "/courses/%s/classes/%s/%s";
    public static final String STUDENT_REF = "/students/%s";

    private static final String[] tableHeaders = {EMAIL, NAME, NUMBER, PROGRAMME_ID, COURSE_ACR};
    private final String email;
    private String pid;
    private int number;
    private String name;
    private List<Klass> classes;
    private boolean specific;

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", pid='" + pid + '\'' +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", classes=" + classes +
                '}';
    }

    public Student(String email, String pid, int number, String name, String cacr) {
        super(tableHeaders);
        this.number = number;
        this.email = email;
        this.pid = pid;
        this.name = name;
    }

    public Student(String email, String pid, String number, String name, List<Klass> assoc) {
        this(email, pid, number, name);
        classes = assoc;
        specific = true;
    }


    public Student(String email, String pid, String number, String name) {
        super(tableHeaders);
        this.number = Integer.parseInt(number);
        this.email = email;
        this.pid = pid;
        this.name = name;
        specific = false;
    }




    public static List<Entity> createStudent(HashMap<String, UniqueTableDto> tables) {
        UniqueTableDto stuTable = tables.get("main");
        UniqueTableDto assocTable = tables.get("assoc");

        List<Entity> list = new LinkedList<>();
        list.add(createStudentStream(stuTable.getRowAt(0), assocTable));

        return list;
    }

    private static Student createStudentStream(UniqueTableDto.SQLRow studentDto, UniqueTableDto assoc) {
        String name = studentDto.getColumnValue("name");
        String email = studentDto.getColumnValue("email");
        String pid = studentDto.getColumnValue("programmeid");
        String number = studentDto.getColumnValue("number");
        LinkedList<Klass> classes = new LinkedList<>();
        for (int i = 0; i < assoc.getRowNumber(); i++) {
            UniqueTableDto.SQLRow rowAt = assoc.getRowAt(i);
            String acr = rowAt.getColumnValue("courseacr");
            String id = rowAt.getColumnValue("cid");
            String sem = rowAt.getColumnValue("semrepresentation");
            classes.add(Klass.createClass(acr, sem, id));
        }

        return new Student(email, pid, number, name, classes);
    }

    public static Stream<Entity> createStudentStream(DTO table) {

        UniqueTableDto studentDto = (UniqueTableDto)table;
        List<Entity> students = new LinkedList<>();
        for (int i = 0; i < studentDto.getRowNumber(); i++) {
            students.add(ofUgly(studentDto.getRowAt(i)));
        }
        return students.stream();
    }
    public static Student ofUgly(UniqueTableDto.SQLRow studentDto) {

        String name = studentDto.getColumnValue("name");
        String email = studentDto.getColumnValue("email");
        String pid = studentDto.getColumnValue("programmeid");
        String number = studentDto.getColumnValue("number");


        return new Student(email, pid, number, name);
    }

    public int getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public List<Klass> getClasses() {
        return classes;
    }

    @Override
    public HTMLElement getHtmlElement() {
        HTMLTr<String> row = new HTMLTr<>();
        row
                .addData(email)
                .addData(name)
                .addData(pid)
                .addData(valueOf(number), "/students/" + number);

        return specific ? addClassReference(row) : row;

    }

    private HTMLTr<String> addClassReference(HTMLTr<String> row) {
        HTMLUL<String> line = new HTMLUL<>();
        setClassReferences(line);
        return row.addData(line.getName());
    }

    private void setClassReferences(HTMLUL<String> line) {
        classes.forEach((Klass klass) -> {
            String courseAcronym = klass.getCourSeAcronym();
            String semester = klass.getSemester();
            String id = klass.getId();
            String ref = format(CLASS_REF, courseAcronym, semester, id);
            HTMLListItem<String> li = new HTMLListItem<>(courseAcronym + semester + id, ref.toLowerCase());
            line.addChild(li);
        });
    }

    @Override
    public String getJson() {
        return "{\"Email\" : " + "\"" + email + "\"," + "\"Name\" : " + "\"" + name
                + "\"," + "\"Number\" : " + "\"" + number + "\"" + "\"Programme\" : " +
                "\"" + pid +"\""+ "\"Classes\" : " + "\"" + classes +"\"" + "}";
    }

    @Override
    public String getPlaintText() {
        return toString();
    }

    public static Stream<Entity> createOneStudent(DTO dto) {
        MultiTableDto table = (MultiTableDto) dto;

        return createStudent(table.getMap()).stream();
    }
}
