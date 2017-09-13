package pt.isel.ls.model.commands.get.list.courses;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;
import static pt.isel.ls.utils.ColumnNames.courseColumnNames;

public class GetProgrammeStructure extends AbstractGet {

    private final String STATEMENT="select distinct courseID,programmeAcr,acronym,name,coordinator from Curricular inner join Courses \n" +
            "            on programmeAcr=? and courseID=acronym";

    public GetProgrammeStructure() {
        super("GET /programmes/{pid}/courses");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        statementInsert =connection.prepareStatement(STATEMENT, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        statementInsert.setString(1,values.get("pid"));

        resultSet = statementInsert.executeQuery();
        return new UniqueTableDto(resultSet, skip, top);
    }

    public  HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("pid",path[2]);
        return map;
    }
}
