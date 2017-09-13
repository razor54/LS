package pt.isel.ls.model.data.entities;


import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.utils.html.HTMLElement;
import pt.isel.ls.utils.html.HTMLTr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static pt.isel.ls.utils.ColumnNames.EMAIL;
import static pt.isel.ls.utils.ColumnNames.NAME;


public class Users extends Entity {

    private static String[] tableHeaders = {EMAIL, NAME, "Type"};
    private final String name;
    private final String email;
    private final String type;
    private final int number;

    List<Users> usersList;

    public Users(String email, String name, String type, int number) {
        super(tableHeaders);
        this.name = name;
        this.type = type;
        this.email = email;
        this.number = number;
    }

    public static Users of(UniqueTableDto.SQLRow userDto) {
        String name = userDto.getColumnValue("name");
        String email = userDto.getColumnValue("email");
        String type = userDto.getColumnValue("tipo");
        int number = Integer.parseInt(userDto.getColumnValue("num"));
        return new Users(email, name, type, number);
    }

    public static Stream<Entity> createOneUser(DTO dto) {
        MultiTableDto table = (MultiTableDto) dto;
        return createUsers(table.getMap()).stream();
    }

    public static List<Users> createUsers(HashMap<String, UniqueTableDto> tables) {
        UniqueTableDto teacherTable = tables.get("users");
        List<Users> users = new LinkedList<>();
        for (int i = 0; i < teacherTable.getRowNumber(); i++) {
            users.add(of(teacherTable.getRowAt(i)));
        }
        return users;
    }


    @Override
    public HTMLElement getHtmlElement() {
        HTMLTr<String> row = new HTMLTr<>();
        row
                .addData(email)
                .addData(name)
                .addData(type,type.equals("s")?"/students":"/teachers");
        //.addData(valueOf(number), "/students/" + number);
        return row;
    }

    @Override
    public String getJson() {
       return  "{\"Email\" : "+"\""+email+"\","+"\"Name\" : "+"\""+name
                +"\","+"\"Type\" : "+"\""+type+"\"}";
    }

    @Override
    public String getPlaintText() {
        return toString();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public static Stream<Entity> createUsersStream(DTO dto) {

        UniqueTableDto studentDto = (UniqueTableDto) dto;
        List<Entity> teachers = new LinkedList<>();
        for (int i = 0; i < studentDto.getRowNumber(); i++) {
            teachers.add(of(studentDto.getRowAt(i)));
        }
        return teachers.stream();
    }

    public int getNumber() {
        return number;
    }
}
