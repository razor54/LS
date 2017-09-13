package pt.isel.ls;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.control.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static pt.isel.ls.control.JDBCConnection.*;

public class JDBCConnectionTests {

    /**
     * Database has to be empty for testing purposes
     **/
    private static final String INSERT_SENTENCE = "insert into Users(email,ProgrammeID,number) values ('miguelmartins@sa.pt','LEIC','1221')";
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";
    private static final String DATABASE_NAME = "LS_TEST";
    private SQLServerDataSource dataSource;//= new SQLServerDataSource();
    private int affectedRows = -1;
    private Connection connection;
    private PreparedStatement statementSelect = null, statementInsert = null, statementDelete = null;
    private ResultSet resultSet;

    @Test
    public void testCreateDataSource() throws SQLServerException {
        dataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        assertNotNull(dataSource);
        assertEquals(DATABASE_NAME, dataSource.getDatabaseName());

    }

    @Before
    public void beforeMethod() {
        dataSource = createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
    }

    @Test
    public void testBeginConnectionWithDatabase() throws SQLServerException {
        // Arrange
        // Act
        connection = beginConnection(dataSource);
        // Assert
        assertNotNull(connection);
    }

    @Test
    public void testCloseConnectionWithDatabase() throws SQLException {
        // Arrange
        boolean closed;
        connection = beginConnection(dataSource);

        // Act
        closed = closeConnection(connection);

        // Assert
        assertTrue(closed);
    }


    private void insertBeforeDelete() throws SQLException {

        try {
            statementInsert = connection.prepareStatement(INSERT_SENTENCE);
            int row = statementInsert != null ? statementInsert.executeUpdate() : 0;
        } catch (SQLException ignored) {
            System.out.println("Didn't insert before delete.");
            System.out.println("Message:");
            System.out.println(ignored.getMessage());
        } finally {
            if (statementInsert != null) statementInsert.close();
        }
    }

    private void deleteBeforeInsert() throws SQLException {
        try {
            statementDelete = connection != null ? connection.prepareStatement("delete Students") : null;
            if (statementDelete != null)
                statementDelete.execute();
        } catch (SQLException ignored) {

            System.out.println("Didn't delete before insert.");
            System.out.println("Message:");
            System.out.println(ignored.getMessage());
        } finally {
            if (statementDelete != null) statementDelete.close();
        }
    }

    private int count(ResultSet r) throws SQLException {
        int i = 0;
        while (r.next()) {
            i++;
        }
        return i;
    }
}
