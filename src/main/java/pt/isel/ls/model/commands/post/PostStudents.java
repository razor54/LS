package pt.isel.ls.model.commands.post;

import java.sql.SQLException;
import java.util.HashMap;

public class PostStudents extends AbstractPost {

    private static final String TYPE = "S";
    private final static String INSERT_USER = "INSERT Into USERS(email, name, tipo, num) " +
            "values (?,?, ?,?)";
    private final static String INSERT_STUDENT = "INSERT Into Students(email, ProgrammeId, number) " +
            "values (?,?,?)";

    public PostStudents() {
        super("POST /students",PostStudents::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {
        try {
            connection.setAutoCommit(false);

            statementInsert = connection != null ? connection.prepareStatement(INSERT_USER) : null;
            statementInsert.setString(1, map.get("email"));
            statementInsert.setString(2, map.get("name"));
            statementInsert.setString(3, TYPE);
            statementInsert.setInt(4, Integer.parseInt(map.get("num")));

            statementInsert.executeUpdate();

            statementInsert = connection.prepareStatement(INSERT_STUDENT);
            statementInsert.setString(1, map.get("email"));
            statementInsert.setString(2, map.get("pid"));
            statementInsert.setInt(3, Integer.parseInt(map.get("num")));

            statementInsert.executeUpdate();
            connection.commit();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException(e.getLocalizedMessage());
        }

    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }

    private static String redirect (HashMap<String,String> map){
        return "/students/"+map.get("num");
    }
}