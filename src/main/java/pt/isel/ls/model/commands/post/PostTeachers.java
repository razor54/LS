package pt.isel.ls.model.commands.post;


import java.sql.SQLException;
import java.util.HashMap;


public class PostTeachers extends AbstractPost {

    private static final String TYPE = "T";
    private final static String INSERT_USER = "INSERT Into USERS(email, name, tipo,num) " +
            "values (?,?, ?,?)";
    private final static String INSERT_TEACHER = "INSERT Into Teachers(number,email) " +
            "values (?,?)";

    public PostTeachers() {
        super("POST /teachers",PostTeachers::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {
        try {
            connection.setAutoCommit(false);
            String email = map.get("email");
            String name = map.get("name");
            int num = Integer.parseInt(map.get("num"));

            statementInsert = connection.prepareStatement(INSERT_USER);
            statementInsert.setString(1, email);
            statementInsert.setString(2, name);
            statementInsert.setString(3, TYPE);
            statementInsert.setInt(4, num);

            if (statementInsert != null) statementInsert.executeUpdate();

            statementInsert = connection.prepareStatement(INSERT_TEACHER);
            statementInsert.setInt(1, num);
            statementInsert.setString(2, email);
            if (statementInsert != null) statementInsert.executeUpdate();

            if (connection != null) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException(e.getLocalizedMessage());

        }
        return false;
    }

    public HashMap<String, String> pathConverter(String[] val, HashMap<String, String> map) {

        return map;
    }
    private static String redirect (HashMap<String,String> map){
        return"/teachers/"+ map.get("num");
    }
}
