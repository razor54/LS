package pt.isel.ls.model.commands.put;

import java.sql.SQLException;
import java.util.HashMap;


public class PutStudents extends PutUsers {

    // TODO check why throwing exception and catching at the same time

    private final String UPDATE_STUDENT = "update Students\n" +
            "set email=?\n" +
            "where number=?";
    private final String GET_EMAIL = "select email from Students where number=?";

    public PutStudents() {
        super("PUT /students/{num}",PutStudents::redirect);
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("num", path[2]);
        return map;
    }

    @Override
    protected boolean updateTable(HashMap map) throws SQLException {
        connection.setAutoCommit(false);
        try {
            put(map, UPDATE_STUDENT, GET_EMAIL);
            return true;

        } catch (SQLException e) {

            if (connection != null) {
                connection.rollback();
            }
        }

        return false;
    }

    private static String redirect (HashMap<String,String> map){
        return "/students/";
    }
}
