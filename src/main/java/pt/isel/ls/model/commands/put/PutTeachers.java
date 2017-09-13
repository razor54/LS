package pt.isel.ls.model.commands.put;


import pt.isel.ls.model.exceptions.CommandNotFoundException;

import java.sql.SQLException;
import java.util.HashMap;


public class PutTeachers extends PutUsers {

    private final String UPDATE_TEACHER = "update Teachers\n" +
            "set email=?\n" +
            "where number=?";
    private final String GET_EMAIL = "select email from Teachers where number=?";

    public PutTeachers() {
        super("PUT /teachers/{num}",PutTeachers::redirect);
    }

    @Override
    public String getBaseCommand() {
        return baseCommand;
    }

    @Override
    protected boolean updateTable(HashMap map) throws SQLException, CommandNotFoundException {
        connection.setAutoCommit(false);
        try {
            put(map, UPDATE_TEACHER, GET_EMAIL);
            return true;
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
//            return false;
        }

    }
    private static String redirect (HashMap<String,String> map){
        return "/teachers/";
    }
    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("num", path[2]);
        return map;
    }
}
