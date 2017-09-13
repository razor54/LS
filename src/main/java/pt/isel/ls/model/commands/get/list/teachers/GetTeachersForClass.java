package pt.isel.ls.model.commands.get.list.teachers;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Teacher;

import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetTeachersForClass extends AbstractGet {

    public static final String STATEMENT =
            "select * from teacher_class_assoc as tca\n" +
                    "inner join  Teachers as t on t.number = tca.teacherNum\n" +
                    "inner join Users as u on u.email = t.email\n" +
                    "where classCourseAcr = ? and classCourseSem = ? and classCID= ?\n";

    public GetTeachersForClass() {
        super("GET /courses/{acr}/classes/{sem}/{num}/teachers");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement(STATEMENT, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
        statementInsert.setString(1, values.get("acr"));
        statementInsert.setString(2, values.get("sem"));
        statementInsert.setString(3, values.get("classId"));
        resultSet = statementInsert.executeQuery();
        return new UniqueTableDto<>(resultSet, skip, top, Teacher::of, "resources/Teachers.html",values.get("UserInput"));
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        map.put("sem", path[4]);
        map.put("classId", path[5]);
        return map;
    }
}
