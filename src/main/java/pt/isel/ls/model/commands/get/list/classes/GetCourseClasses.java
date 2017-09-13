package pt.isel.ls.model.commands.get.list.classes;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Klass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetCourseClasses extends AbstractGet {

    public static final String STATEMENT = "select * from Classes \n" +
            "where courseACR = ? ";

    public GetCourseClasses() {
        super("GET /courses/{acr}/classes");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert =connection.prepareStatement(STATEMENT, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY) ;
        statementInsert.setString(1, values.get("acr"));
        resultSet =  statementInsert.executeQuery();
        return new UniqueTableDto<>(resultSet, skip, top, Klass::of, "resources/CourseClasses.html",values.get("UserInput"));
    }

    public  HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr",path[2]);
        return map;
    }
}
