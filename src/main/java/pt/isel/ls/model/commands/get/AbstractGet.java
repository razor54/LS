package pt.isel.ls.model.commands.get;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.SQLException;
import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static pt.isel.ls.control.JDBCConnection.beginConnection;
import static pt.isel.ls.control.JDBCConnection.closeConnection;

public abstract class AbstractGet extends AbstractCommand<DTO> {

    protected AbstractGet(String command) {
        super(command);
    }

    @Override
    public final DTO execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException {
        connection = beginConnection(sqlServerDataSource);
        DTO toRet;
        try {
            toRet = executeQuery(values, parseInt(values.get("skip")), parseInt(values.get("top")));
            if(toRet instanceof UniqueTableDto)
                ((UniqueTableDto) toRet).setUserInput(values.get("UserInput"));
        } finally {
            if (statementInsert != null) statementInsert.close();
            if (connection != null) closeConnection(connection);
        }
        return toRet;
    }

    protected abstract DTO executeQuery(HashMap<String, String> values, int skip, int top) throws SQLException;

    @Override
    public String getBaseCommand() {
        return baseCommand;
    }


}
