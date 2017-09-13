package pt.isel.ls.model.commands.post;

import java.sql.SQLException;
import java.util.HashMap;


public class PostClassOnCourse extends AbstractPost {

    private final String INSERT = "insert into Classes (courseACR,semRepresentation,cID) " +
            "values (?,?,?)";

    public PostClassOnCourse() {
        super("POST /courses/{acr}/classes",PostClassOnCourse::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {
        statementInsert = connection != null ? connection.prepareStatement(INSERT) : null;
        statementInsert.setString(1, map.get("acr"));
        statementInsert.setString(2, map.get("sem"));
        statementInsert.setString(3, map.get("num"));
        if (statementInsert != null) statementInsert.executeUpdate();
        return connection != null;
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("acr", path[2]);
        return map;

    }

    private static String redirect (HashMap<String,String> map){
        return "/courses/"+map.get("acr") +"/classes/"+map.get("sem")+"/"+map.get("num");
    }
}

