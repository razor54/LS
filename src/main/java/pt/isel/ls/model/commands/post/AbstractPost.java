package pt.isel.ls.model.commands.post;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.model.data.dtos.MessageDto;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

import static pt.isel.ls.control.JDBCConnection.beginConnection;
import static pt.isel.ls.control.JDBCConnection.closeConnection;


public abstract class AbstractPost extends AbstractCommand<MessageDto> {


    private Function<HashMap<String, String>, String> linkCreator;

    AbstractPost(String str) {
        super(str);
    }

    AbstractPost(String str, Function<HashMap<String, String>, String> link) {
        super(str);
        linkCreator = link;
    }

    @Override
    public final MessageDto execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException {
        connection = beginConnection(sqlServerDataSource);

        boolean toRet;

        try {
            toRet = postInsert(values);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            return new MessageDto(e.getLocalizedMessage(), HttpStatusCode.BadRequest);
        } catch (NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
            return new MessageDto(e.getLocalizedMessage(), HttpStatusCode.BadRequest);
        } finally {
            if (statementInsert != null) statementInsert.close();
            if (connection != null) closeConnection(connection);
        }
        return new MessageDto("Post done witch success", HttpStatusCode.SeeOther);

    }

    public Function<HashMap<String, String>, String>  getDirectioner() {
        return linkCreator;
    }

    @Override
    public String getBaseCommand() {
        return baseCommand;
    }

    protected abstract boolean postInsert(HashMap<String, String> map) throws SQLException;
}
