package pt.isel.ls.model.commands.get.element;

import pt.isel.ls.model.commands.get.AbstractGet;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MultiTableDto;
import pt.isel.ls.model.data.dtos.UniqueTableDto;
import pt.isel.ls.model.data.entities.Teacher;

import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class GetTeacher extends AbstractGet {

    public static final String SELECT_FROM_TEACHERS =
            "  SELECT u.email, u.name, t.number FROM USERS as u " +
                    "INNER JOIN TEACHERS as t ON u.email = t.email " +
                    "where t.number = ?";

    public static final String SELECT_FROM_TCA =
            " select * from teacher_class_assoc as tca\n" +
                    "where tca.teacherNum = ?";


    public GetTeacher() {
        super("GET /teachers/{num}");
    }

    @Override
    protected DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException {
        UniqueTableDto teacher = getTeacher(values, skip, top);
        UniqueTableDto assoc = getTeacherAssoc(values, skip, top);
        HashMap<String, UniqueTableDto> view = new HashMap<>();
        view.put("main", teacher);
        view.put("assoc", assoc);

        return new MultiTableDto<>(Teacher::createTeacher, view,"resources/SpecificTeacher.html");
    }

    private UniqueTableDto getTeacher(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_FROM_TEACHERS);
    }

    private UniqueTableDto getTeacherAssoc(HashMap<String, String> values, int skip, int top) throws SQLException {
        return getTableOf(values, skip, top, SELECT_FROM_TCA);
    }


    private UniqueTableDto getTableOf(HashMap<String, String> values, int skip, int top, String selectFromStudents) throws SQLException {
        statementInsert = connection.prepareStatement(selectFromStudents, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
        statementInsert.setString(1, values.get("tecNum"));

        return new UniqueTableDto<>(statementInsert.executeQuery(), skip, top);
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("tecNum", path[2]);
        return map;
    }
}
