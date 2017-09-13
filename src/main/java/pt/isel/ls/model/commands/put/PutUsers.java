package pt.isel.ls.model.commands.put;

import pt.isel.ls.utils.Utils;
import pt.isel.ls.model.exceptions.CommandNotFoundException;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.function.Function;


public abstract class PutUsers extends AbstractPut {

    protected final String UPDATE_USER = "update Users\n" +
            "\tset name = ?, email=?\n" +
            "\twhere email=?";

    protected PutUsers(String command) {
        super(command);
    }
    protected PutUsers (String command, Function<HashMap<String, String>, String> directioner) {
        super(command,directioner);
    }

    protected void put(HashMap<String, String> parameters, String UPDATE, String GET_EMAIL) throws SQLException, CommandNotFoundException {

        int number = Integer.parseInt(parameters.get("num"));
        statementInsert = connection.prepareStatement(GET_EMAIL);
        statementInsert.setInt(1, number);
        resultSet = statementInsert.executeQuery();

        String[] result = Utils.printResultSet(resultSet).split(" ");
        if (result.length == 1)
            throw new CommandNotFoundException("sem utilizador");
        String email = result[1]
                .replace("\n", "");


        statementInsert = connection.prepareStatement(UPDATE);
        statementInsert.setNull(1, Types.VARCHAR);
        statementInsert.setInt(2, number);
        statementInsert.executeUpdate();

        statementInsert = connection.prepareStatement(UPDATE_USER);
        statementInsert.setString(1, parameters.get("name"));
        statementInsert.setString(2, parameters.get("email"));
        statementInsert.setString(3, email);
        statementInsert.executeUpdate();

        statementInsert = connection.prepareStatement(UPDATE);
        statementInsert.setString(1, parameters.get("email"));
        statementInsert.setInt(2, number);
        statementInsert.executeUpdate();

        connection.commit();
    }


}
