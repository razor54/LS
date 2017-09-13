package pt.isel.ls.model.commands.root;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.model.data.dtos.StaticDto;

import java.sql.SQLException;
import java.util.HashMap;

public class GetAll extends AbstractCommand<StaticDto> {

    public GetAll() {
        super("GET /");
    }

    @Override
    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        return map;
    }



    @Override
    public StaticDto execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException {
        return new StaticDto("public/views/All.html");
    }
}