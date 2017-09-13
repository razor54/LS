package pt.isel.ls.model.commands;

import pt.isel.ls.model.data.dtos.DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;


//todo A string parser
public abstract class AbstractCommand<T extends DTO> implements ICommand<T> {
    public final static int HAS_HEADER_OR_PARAMETERS = 3;
    public final static int HAS_HEADER_AND_PARAMETERS = 4;
    public static String PATH_SEPARATOR_CHAR = "/";


    public static String COMMAND_SEPARATOR_CHAR = " ";
    public static String PARAMETERS_SEPARATOR_CHAR = "&";
    public static char PARAMETERS_VALUE_SEPARATOR = '=';
    protected static int HEADER_INDEX = 2;
    protected static int MAX_COMMAND_LENGTH = 3; // TODO check if it is get or post  | reValue in get/post
    protected static int PARAMETERS_INDEX = 2;
    protected static int MAX_PARAMETERS = 5;
    static int METHOD = 0;
    static int PATH = 1;
    public String next, previous, root;
    protected Connection connection;
    protected PreparedStatement statementInsert = null;
    protected ResultSet resultSet = null;
    protected String baseCommand;

    protected AbstractCommand(String command) {
        baseCommand = command;
    }

    public abstract HashMap<String, String> pathConverter(String path[], HashMap<String, String> map);

    public String getBaseCommand() {
        return baseCommand;
    }

    public String getPath() {
        return baseCommand.split(" ")[1];
    }
}


