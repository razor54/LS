package pt.isel.ls.model.commands.get.element;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Programme;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetProgramme extends AbstractGet {

    private static final String SELECT_PROGRAMME = "Select * from programmes where pid= ?";
    private static final String SELECT_PROGRAM_ASSOC =
            "  select distinct name,PID, length, courseID from Curricular\n" +
                    "INNER JOIN Programmes as p ON pid = ? and pid=programmeAcr\n";

    public GetProgramme() {
        super("GET /programmes/{pid}");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        HashMap<String, UniqueTableDto> tables = new HashMap<>();
        UniqueTableDto programme = getProgramme(values, skip, top);
        if (programme.getRowNumber() == 0)
            ;//TODO exception: NO ELEMENTS WITH GIVEN DATA
        tables.put("main", programme);
        tables.put("courses", getCourses(values, skip, top));

        return new MultiTableDto<>(Programme::createProgramme, tables);
    }

    /**
     * Method that selects the courses where the programme is present
     *
     * @param values map used to get query values
     * @param skip   number of elements to skip
     * @param top    length of the sequence
     * @return A Sql table with one or more rows depending on the number of courses the programme has.
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getCourses(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_PROGRAM_ASSOC);
    }

    /**
     * Method that selects a specific programme from the DB
     *
     * @param values map used to get query values
     * @param skip   number of elements to skip
     * @param top    length of the sequence
     * @return A Sql table with one row, a programme.
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getProgramme(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_PROGRAMME);
    }

    /**
     * Generates a sql Table with the given select query
     *
     * @param values      map used to get query values
     * @param skip        number of elements to skip
     * @param top         length of the sequence
     * @param selectQuery query used to select
     * @return returns a new table with the query result
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getTableOf(HashMap<String, String> values, int skip, int top, String selectQuery) throws SQLException {
        PreparedStatement statementInsert = connection.prepareStatement(selectQuery, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
        statementInsert.setString(1, values.get("pid"));
        return new UniqueTableDto(statementInsert.executeQuery(), skip, top);
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("pid", path[2]);
        return map;
    }
}
