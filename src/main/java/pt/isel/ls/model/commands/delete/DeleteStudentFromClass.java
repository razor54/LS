package pt.isel.ls.model.commands.delete;

import java.sql.SQLException;
import java.util.HashMap;

public class DeleteStudentFromClass extends AbstractDelete {

    public DeleteStudentFromClass() {
        super("DELETE /courses/{acr}/classes/{sem}/{num}/students/{numStu}",DeleteStudentFromClass::redirect);
    }

    @Override
    protected boolean executeUpdate(HashMap<String, String> values) throws SQLException {
        int row = 0;
        statementInsert = connection != null ? connection.prepareStatement("delete from student_class_assoc\n" +
                "where number = ? and courseACR = ? and semRepresentation = ? and cID = ?")
                : null;
        statementInsert.setInt(1, Integer.parseInt(values.get("numStu")));
        statementInsert.setString(2, values.get("acr"));
        statementInsert.setString(3, values.get("sem"));
        statementInsert.setString(4, values.get("numClass"));
        if (statementInsert != null) row = statementInsert.executeUpdate();
        return connection != null;
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        map.put("sem", path[4]);
        map.put("numClass", path[5]);
        map.put("numStu", path[7]);
        return map;
    }

    private static String redirect (HashMap<String,String> map){
        return "/courses/"+map.get("acr") +"/classes/"+map.get("sem")+"/"+map.get("classNum")+"/students";
    }

}
