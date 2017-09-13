package pt.isel.ls.model.commands.get.element;

import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetClass extends AbstractGet {

    private static final String STATEMENT = "select * from classes " +
            "where courseACR=? " +
            "and semRepresentation=? " +
            "and cid=?";

    private static final String STATEMENT_TCA =
            "select name,t.email,number from teacher_class_assoc as tca\n" +
                    "inner join Teachers as t\n" +
                    "on t.number =tca.teacherNum \n" +
                    "and tca.classCourseAcr = ?\n" +
                    "and tca.classCourseSem = ? \n" +
                    "and tca.classCID = ? \n" +
                    "inner join  Users as u on u.email = t.email";
    private static final String STATEMENT_SCA =
            "select name ,s.email,s.number,ProgrammeId from student_class_assoc as sca\n" +
            "inner join Students as s\n" +
            "on sca.courseAcr = ?\n" +
            "and sca.semRepresentation = ? \n" +
            "and sca.cID = ? \n" +
            "and sca.number = s.number \n" +
            "inner join  Users as u on u.email = s.email";

    public GetClass() {
        super("GET /courses/{acr}/classes/{sem}/{num}");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        //EXECUTION
        UniqueTableDto klass = getClass(values, skip, top);
        if (klass.getRowNumber() == 0) {/*TODO exception : nothing to show*/}
        HashMap<String, UniqueTableDto> tables = new HashMap<>();
        tables.put("main", klass);
        tables.put("teachers", getTeacherInfo(values, skip, top));
        tables.put("students", getStudentsInfo(values, skip, top));

        return new MultiTableDto(tables,HttpStatusCode.Ok);


    }

    private UniqueTableDto getStudentsInfo(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values,skip,top, STATEMENT_SCA);
    }

    private UniqueTableDto getClass(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, STATEMENT);
    }

    private UniqueTableDto getTeacherInfo(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, STATEMENT_TCA);
    }

    private UniqueTableDto getTableOf(HashMap<String, String> values, int skip, int top, String selectFromStudents) throws SQLException {
        PreparedStatement statementInsert = connection.prepareStatement(selectFromStudents, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
        statementInsert.setString(1, values.get("acr"));
        statementInsert.setString(2, values.get("sem"));
        statementInsert.setString(3, values.get("class"));
        return new UniqueTableDto(statementInsert.executeQuery(), skip, top);
    }


    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        map.put("sem", path[4]);
        map.put("class", path[5]);
        return map;
    }
}
