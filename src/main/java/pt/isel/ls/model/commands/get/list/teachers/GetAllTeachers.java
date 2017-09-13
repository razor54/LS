package pt.isel.ls.model.commands.get.list.teachers;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;


public class GetAllTeachers extends AbstractGet {

    public static final String SELECT_FROM_TEACHERS =
            "select name,t.email, number from Teachers as t " +
                    "inner join users as u on u.email =t.email ";

    public GetAllTeachers() {
        super("GET /teachers");
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement(SELECT_FROM_TEACHERS, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statementInsert.executeQuery();
        return new UniqueTableDto<>(resultSet, skip, top);
    }
}
