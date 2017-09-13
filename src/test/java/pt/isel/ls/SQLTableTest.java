package pt.isel.ls;

import org.junit.Test;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.fail;
import static pt.isel.ls.control.JDBCConnection.*;


public class SQLTableTest {

    @Test
    public void should_create_a_Table() throws SQLException {
        Connection connection = null;

        try {
            connection = beginConnection(createDataSource("Credentials"));
            ResultSet r = connection.prepareStatement("select * from Users", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
            UniqueTableDto t = new UniqueTableDto(r, 0, Integer.MAX_VALUE);

//            t.forEach(System.out::println);
        } catch (SQLException ignored) {
            fail(ignored.getMessage());
        } finally {
            if (connection != null) closeConnection(connection);
        }
    }

}