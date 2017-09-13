package pt.isel.ls.model.commands.post;


import java.sql.SQLException;
import java.util.HashMap;


public class PostProgrammes extends AbstractPost {

    private final String INSERT = "Insert into Programmes(name, PID,length) " +
            "values (?,?,?)";

    public PostProgrammes() {
        super("POST /programmes",PostProgrammes::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {
        statementInsert = connection != null ? connection.prepareStatement(INSERT) : null;
        statementInsert.setString(1, map.get("name"));
        statementInsert.setString(2, map.get("pid"));
        statementInsert.setInt(3, Integer.parseInt(map.get("length")));

        if (statementInsert != null) statementInsert.executeUpdate();
        return connection != null;
    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }
    private static String redirect (HashMap<String,String> map){
        return "/programmes/"+map.get("pid");
    }
}
