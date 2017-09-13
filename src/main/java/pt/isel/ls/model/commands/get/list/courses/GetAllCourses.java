package pt.isel.ls.model.commands.get.list.courses;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetAllCourses extends AbstractGet {

    public GetAllCourses() {
        super("GET /courses");
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert = connection.prepareStatement("select * from Courses", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statementInsert.executeQuery();

        return new UniqueTableDto<>(resultSet, skip, top);
    }
}

// TODO
// NEW SQL TABLE AND PASS ENTITY TO REPRESENT BECAUSE EACH ENTITY
// HAS A DIFFERENT TABLE STRUCTURE, DIFFERENT NEXT AND PREVIOUS
//
// CREATE DIFFERENT ROWS AND WHEN ADDING DATA CHECK IF COLUMN IS
// THE MAIN COLUMN AND IF SO ADD CHILD WITH REF INSTEAD OF ONLY THE VALUE