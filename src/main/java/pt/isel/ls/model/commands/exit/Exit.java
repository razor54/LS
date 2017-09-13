package pt.isel.ls.model.commands.exit;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.Result.BoolResult;
import pt.isel.ls.Result.IResult;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MessageDto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static pt.isel.ls.utils.ExceptionLog.getExceptions;
import static pt.isel.ls.utils.ResultLog.getResults;

public class Exit extends AbstractCommand<MessageDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Exit.class.getName());
    private List<Exception> exceptions;
    private List<IResult> cache;

    public Exit() {
        super("EXIT /");
        exceptions = getExceptions();
        cache = getResults();
    }

    private void writeToFile(List<Exception> exceptions, List<IResult> cache) {
        String fileName = "./src/main/resources/log.txt";
        try (FileWriter fileWriter = new FileWriter(new File(fileName), false)) {
            fileWriter.write("Exceptions: \n");
            for (Exception exception : exceptions) {
                fileWriter.write(exception.getMessage() + "\n");
            }
            fileWriter.write("\nResults: \n");
            for (IResult iResult : cache) {
                fileWriter.write(iResult.getResult().toString() + "\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
//            System.out.println(e.getMessage());
            LOGGER.error("Error writing to file: " + e.getMessage());
        }

    }

    @Override
    public MessageDto  execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException {
        writeToFile(exceptions, cache);
        return new MessageDto("Command options showed with success", HttpStatusCode.Ok);
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }


}
