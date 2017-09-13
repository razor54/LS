package pt.isel.ls.model.commands;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.model.data.dtos.DTO;

import java.sql.SQLException;
import java.util.HashMap;

public interface ICommand<T extends DTO> {
    // generic because different commands return different types of dto
    T execute(SQLServerDataSource sqlServerDataSource, HashMap<String, String> values) throws SQLException;

    String getBaseCommand();

    String getPath();
}
