package pt.isel.ls.model.commands.post;

import java.sql.SQLException;
import java.util.HashMap;


public class PostStudentToClass extends AbstractPost {
    private final String POST = "INSERT INTO student_class_assoc(number, courseACR, semRepresentation , cID )\n" +
            "values(?,?,?,?)";

    public PostStudentToClass() {
        super("POST /courses/{acr}/classes/{sem}/{num}/students",PostStudentToClass::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {
        try {
            if (connection != null)
                connection.setAutoCommit(false);
            int numReps = 0;
            String key = "numStu";
            int actNumStu = Integer.parseInt(map.get(key));
            while (actNumStu != -1) {
                statementInsert = connection != null ? connection.prepareStatement(POST) : null;
                statementInsert.setInt(1, actNumStu);
                statementInsert.setString(2, map.get("acr"));
                statementInsert.setString(3, map.get("sem"));
                statementInsert.setString(4, map.get("classNum"));
                statementInsert.executeUpdate();

                if (map.containsKey(key + (++numReps))) {
                    actNumStu = Integer.parseInt(map.get(key + numReps));
                } else actNumStu = -1;

            }
            assert connection != null;
            connection.commit();

            return true;

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException(e.getLocalizedMessage());
        }
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        map.put("sem", path[4]);
        map.put("classNum", path[5]);
        return map;
    }

    private static String redirect (HashMap<String,String> map){
        return"/courses/"+map.get("acr") +"/classes/"+map.get("sem")+"/"+map.get("classNum")+"/students";
    }
}
