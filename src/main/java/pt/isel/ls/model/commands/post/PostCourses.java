package pt.isel.ls.model.commands.post;

import java.sql.SQLException;
import java.util.HashMap;


public class PostCourses extends pt.isel.ls.model.commands.post.AbstractPost {
    private static final String INSERT_COURSE = "INSERT Into Courses(name, acronym, coordinator) " +
            "values (?,?,?)";

    public PostCourses() {
        super("POST /courses",PostCourses::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {

        statementInsert = connection.prepareStatement(INSERT_COURSE) ;
        statementInsert.setString(1, map.get("name"));
        statementInsert.setString(2, map.get("acr"));
        statementInsert.setInt(3, Integer.parseInt(map.get("teacher")));
        if (statementInsert != null) statementInsert.executeUpdate();
        return connection != null;
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }

    private static String redirect (HashMap<String,String> map){
        return "/courses/"+map.get("acr");
    }
}
