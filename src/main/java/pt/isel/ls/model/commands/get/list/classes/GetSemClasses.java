package pt.isel.ls.model.commands.get.list.classes;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;
import static pt.isel.ls.utils.ColumnNames.classColumnNames;

public class GetSemClasses extends AbstractGet {

    public static final String STATEMENT =
            "select * from classes where courseACR=? and semRepresentation=?";

    public GetSemClasses() {
        super("GET /courses/{acr}/classes/{sem}");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert =connection.prepareStatement(STATEMENT, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY) ;
        //PREPARATION
        statementInsert.setString(1, values.get("acr"));
        statementInsert.setString(2, values.get("sem"));
        //EXECUTION
        resultSet = statementInsert.executeQuery()  ;
        return new UniqueTableDto(resultSet, skip, top);
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        map.put("sem", path[4]);
        return map;
    }
}
