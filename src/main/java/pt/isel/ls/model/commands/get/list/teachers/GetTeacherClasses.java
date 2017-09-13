package pt.isel.ls.model.commands.get.list.teachers;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static pt.isel.ls.utils.ColumnNames.classColumnNames;

public class GetTeacherClasses extends AbstractGet {

    private final String STATEMENT =
            "select distinct * from teacher_class_assoc where teacherNum=?";

    public GetTeacherClasses() {
        super("GET /teachers/{num}/classes");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement(STATEMENT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statementInsert.setInt(1, Integer.parseInt(values.get("tecNum")));
        resultSet = statementInsert != null ? statementInsert.executeQuery() : null;
        return new UniqueTableDto(resultSet, skip, top);
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("tecNum", path[2]);
        return map;
    }

}
