package pt.isel.ls.model.commands.get.list.students;


import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetClassStudents extends AbstractGet {
    protected String STATEMENT = "select Students.email,name,Students.ProgrammeID,Students.number \n" +
            "            from student_class_assoc inner join Students \n" +
            "            on cid=? and courseACR=? and semRepresentation=? \n" +
            "            and Students.number=student_class_assoc.number \n" +
            "            inner join Users on Users.email=Students.email";

    public GetClassStudents() {
        super("GET /courses/{acr}/classes/{sem}/{num}/students");
    }

    protected GetClassStudents(String command) {
        super(command);
    }

    public static AbstractCommand intance() {
        return new GetClassStudents();
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement(STATEMENT, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //PREPARATION
        statementInsert.setString(1, values.get("semNum"));
        statementInsert.setString(2, values.get("acr"));
        statementInsert.setString(3, values.get("sem"));
        //EXECUTION
        resultSet = statementInsert.executeQuery();
        return new UniqueTableDto<>(resultSet, skip, top, Student::ofUgly, "resources/Students.html",values.get("UserInput"));
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        map.put("sem", path[4]);
        map.put("semNum", path[5]);
        return map;
    }
}
