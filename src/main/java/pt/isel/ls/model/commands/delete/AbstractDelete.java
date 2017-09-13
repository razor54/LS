package pt.isel.ls.model.commands.delete;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.Result.BoolResult;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MessageDto;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

import static pt.isel.ls.control.JDBCConnection.beginConnection;
import static pt.isel.ls.control.JDBCConnection.closeConnection;


public abstract class AbstractDelete extends AbstractCommand<MessageDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDelete.class.getName());
    private Function<HashMap<String, String>, String> linkCreator;

    AbstractDelete(String command) {
        super(command);
    }

    AbstractDelete(String command, Function<HashMap<String, String>, String> linkCreator) {
        super(command);
        this.linkCreator=linkCreator;
    }


    public MessageDto execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException {
        connection = beginConnection(sqlServerDataSource);

        boolean done = false;

        try {
            done = executeUpdate(values);
        } catch (SQLException e) {
            LOGGER.info("Unable to update : " + e);
            return  new MessageDto("Bad delete", HttpStatusCode.BadRequest);

        } finally {
            if (statementInsert != null) statementInsert.close();
            if (connection != null) closeConnection(connection);
        }

        return new MessageDto("Good delete",HttpStatusCode.Ok);
    }

    protected abstract boolean executeUpdate(HashMap<String, String> values) throws SQLException;

    @Override
    public String getBaseCommand() {
        return baseCommand;
    }

    public Function<HashMap<String, String>, String> getLinkCreator() {
        return linkCreator;
    }
}
