package pt.isel.ls.model.commands.listen;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.Result.GetResult;
import pt.isel.ls.control.http.FirstHttpServer;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MessageDto;

import java.sql.SQLException;
import java.util.HashMap;


public class Listen extends AbstractCommand<MessageDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listen.class.getName());

    public Listen() {
        super("LISTEN /");
    }

    @Override
    public MessageDto execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException {
        startServer();
        return new MessageDto("ServerOperational", HttpStatusCode.Ok);
    }

    // TODO check exception
    private void startServer() {
        try {
            new FirstHttpServer().startServer();
        } catch (Exception e) {
           e.printStackTrace();
            LOGGER.error("Error starting server: " + e.getMessage());
        }
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }
}
