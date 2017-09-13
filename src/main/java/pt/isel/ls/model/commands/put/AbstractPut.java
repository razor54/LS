package pt.isel.ls.model.commands.put;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.Result.BoolResult;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MessageDto;
import pt.isel.ls.model.exceptions.CommandNotFoundException;
import pt.isel.ls.view.htmlViews.View;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

import static pt.isel.ls.control.JDBCConnection.beginConnection;
import static pt.isel.ls.control.JDBCConnection.closeConnection;

public abstract class AbstractPut extends AbstractCommand<MessageDto> {


    private Function<HashMap<String, String>, String> directioner;

    protected AbstractPut(String command) {
        super(command);
    }
    protected AbstractPut(String command, Function<HashMap<String, String>, String> directioner) {
        super(command);
        this.directioner=directioner;
    }



    @Override
    public MessageDto execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException, CommandNotFoundException {
        connection = beginConnection(sqlServerDataSource);

        boolean done;

        try {
            done = updateTable(values);
        } finally {
            if (statementInsert != null) statementInsert.close();
            if (connection != null) closeConnection(connection);
        }

        return new MessageDto("Changed data with success", HttpStatusCode.Ok);
    }


    protected abstract boolean updateTable(HashMap<String, String> map) throws SQLException;

    public Function<HashMap<String,String>,String> getDirectioner() {
        return directioner;
    }
}
