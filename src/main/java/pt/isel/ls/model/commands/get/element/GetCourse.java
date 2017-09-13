package pt.isel.ls.model.commands.get.element;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Course;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetCourse extends AbstractGet {


    private static final String SELECT_COURSE = "Select * from Courses where acronym=?";
    private static final String SELECT_SEMESTRES =
            "select distinct curricularsem from Curricular\n" +
                    "WHERE courseID = ?";
    private static final String SELECT_PROGRAM_ASSOC =
            "\tselect distinct name,PID, length, courseID from Curricular as pca\n" +
                    " INNER JOIN Programmes as p ON pca.courseID = ? and programmeAcr=pid";

    public GetCourse() {
        super("GET /courses/{acr}");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        HashMap<String, UniqueTableDto> tables = new HashMap<>();
        UniqueTableDto course = getCourse(values, skip, top);
        if (course.getRowNumber() == 0) ;//TODO exception
        tables.put("main", course);
        tables.put("sems", getSemesters(values, skip, top));
        tables.put("programmes", getProgrammes(values, skip, top));

        return new MultiTableDto<>(Course::createCourse, tables);
    }

    /**
     * Method that selects a semesters where the course is present
     *
     * @param values map used to get query values
     * @param skip   number of elements to skip
     * @param top    length of the sequence
     * @return A Sql table with one or more rows depending on the number of semesters the course is in.
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getSemesters(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_SEMESTRES);
    }

    /**
     * Method that selects a specific course from the DB
     *
     * @param values map used to get query values
     * @param skip   number of elements to skip
     * @param top    length of the sequence
     * @return A Sql table with one row, a course.
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getCourse(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_COURSE);
    }

    /**
     * Method that selects the programmes where this course is present
     *
     * @param values map used to get query values
     * @param skip   number of elements to skip
     * @param top    length of the sequence
     * @return A Sql table with the number of programmes present in the table
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getProgrammes(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_PROGRAM_ASSOC);
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
        statementInsert.setString(1, values.get("acr"));
        return new UniqueTableDto(statementInsert.executeQuery(), skip, top);
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        return map;
    }
}
