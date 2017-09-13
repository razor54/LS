package pt.isel.ls.model.commands.get.list;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;


public class GetAllUsers extends AbstractGet {
    public GetAllUsers() {
        super("GET /users");
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }

    @Override
    protected UniqueTableDto executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement("select * from Users", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statementInsert.executeQuery();
        return new UniqueTableDto<>(resultSet, skip,top, Users::of,"resources/Users.html",values.get("UserInput"));
    }
}
