package pt.isel.ls.model.commands.get.list.students;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;


public class GetAllStudents extends AbstractGet {

    public static final String SELECT_FROM_STUDENTS = "select u.name,u.email, s.ProgrammeId,s.number from Students as s" +
            " inner join Users as u on u.email = s.email";

    public GetAllStudents() {
        super("GET /students");
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement(SELECT_FROM_STUDENTS, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statementInsert.executeQuery();
        return new UniqueTableDto(resultSet, skip, top);
    }

    // values.get("UserInput")
}
