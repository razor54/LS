package pt.isel.ls.model.commands.post;

import java.sql.SQLException;
import java.util.HashMap;


public class PostTeacherToClass extends AbstractPost {

    private final String STATEMENT = "insert into teacher_class_assoc (teacherNum,classCourseAcr,classCourseSem,classCID)" +
            "values(?,?,?,?)";

    public PostTeacherToClass() {
        super("POST /courses/{acr}/classes/{sem}/{num}/teachers",PostTeacherToClass::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {
        statementInsert = connection != null ? connection.prepareStatement(STATEMENT) : null;

        statementInsert.setInt(1, Integer.parseInt(map.get("numDoc")));
        statementInsert.setString(2, map.get("acr"));
        statementInsert.setString(3, map.get("sem"));
        statementInsert.setString(4, map.get("classNum"));

        if (statementInsert != null) statementInsert.executeUpdate();
        return connection != null;
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        map.put("sem", path[4]);
        map.put("classNum", path[5]);
        return map;
    }

    private static String redirect (HashMap<String,String> map){
        return"/courses/"+map.get("acr") +"/classes/"+map.get("sem")+"/"+map.get("classNum")+"/teachers";
    }
}
