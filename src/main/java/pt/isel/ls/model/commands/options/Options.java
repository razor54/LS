package pt.isel.ls.model.commands.options;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.Result.BoolResult;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.MessageDto;
import pt.isel.ls.model.data.dtos.StaticDto;

import java.sql.SQLException;
import java.util.HashMap;

public class Options extends AbstractCommand<StaticDto> {
    public Options() {
        super("OPTIONS /");
    }

    @Override
    public StaticDto execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException {
        pt.isel.ls.utils.Options.printOptions();
        return new StaticDto("options.html");
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }
}
