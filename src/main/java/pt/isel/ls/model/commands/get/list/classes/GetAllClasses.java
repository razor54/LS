package pt.isel.ls.model.commands.get.list.classes;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Klass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;


public class GetAllClasses extends AbstractGet {

    public GetAllClasses() {
        super("GET /classes");
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement("select * from Classes", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statementInsert.executeQuery();
        return new UniqueTableDto<>(resultSet, skip, top, Klass::of, "resources/Classes.html",values.get("UserInput"));
    }
}
