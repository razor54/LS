package pt.isel.ls.model.commands.get.element;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.entities.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetStudent extends AbstractGet {


    public static final String SELECT_FROM_STUDENTS =
            "  SELECT s.email, u.name, s.number,s.programmeID FROM USERS as u " +
                    "INNER JOIN STUDENTS as s ON u.email = s.email " +
                    "where s.number = ?";


    public static final String SELECT_FROM_SCA =
            " select * from student_class_assoc as sca\n" +
                    "where sca.number = ?";

    public GetStudent() {
        super("GET /students/{num}");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {

        HashMap<String, UniqueTableDto> tables = new HashMap<>();
        tables.put("main", getStudent(values, skip, top));
        tables.put("assoc", getClassInfo(values, skip, top));

        return new MultiTableDto<>(Student::createStudent, tables, "resources/SpecificStudent.html");
    }

    /**
     * Method that selects a specific student association from the DB
     *
     * @param values map used to get query values
     * @param skip   number of elements to skip
     * @param top    length of the sequence
     * @return A Sql table with one or more rows depending on the number of classes the student belongs to.
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getClassInfo(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_FROM_SCA);
    }

    /**
     * Method that selects a specific student from the DB
     *
     * @param values map used to get query values
     * @param skip   number of elements to skip
     * @param top    length of the sequence
     * @return A Sql table with one row, a student.
     * @throws SQLException exception thrown by a wrong query or a problem with the database
     */
    private UniqueTableDto getStudent(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_FROM_STUDENTS);
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
        statementInsert.setString(1, values.get("stuNum"));
        return new UniqueTableDto(statementInsert.executeQuery(), skip, top);
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("stuNum", path[2]);
        return map;

    }
}
