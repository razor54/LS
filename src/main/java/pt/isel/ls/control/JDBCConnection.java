package pt.isel.ls.control;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

/**
 * No need for private static fields.
 * Used it as a dataource init provider  and a way
 * to get a connection
 */
public class JDBCConnection {

    private static final Logger LOGGER = Logger.getLogger(JDBCConnection.class.getName());

    public static SQLServerDataSource createDataSource() {
        LOGGER.info("Starting database configuration...");
        SQLServerDataSource source = new SQLServerDataSource();
        start(source/*, environmentVariableName*/);
        LOGGER.info("Database configuration done.");
        return source;
    }

    public static SQLServerDataSource createDataSource(String environmentVariableName) {
        LOGGER.info("Starting database configuration...");
        SQLServerDataSource source = new SQLServerDataSource();
        start(source, environmentVariableName);
        LOGGER.info("Database configuration done.");
        return source;
    }

    /**
     * Reads the database credentials from an environment variable named "Credentials"
     * and assigns prepares them to be ready to set to the DataSource,
     * Must have set 'Environment variable' with the name 'Credentials'
     * The content shall be a string separated by ';'
     * E.g: user;password;serverName;DatabaseName
     */
    private static void start(SQLServerDataSource dataSource) {
        setDS(dataSource);
    }

    private static void start(SQLServerDataSource dataSource, String credentials) {
        String user_info = System.getenv(credentials);
        String[] info = user_info.split(";");
        setDS(dataSource, info);
    }

    /**
     * Sets the DataSource information
     */
    private static void setDS(SQLServerDataSource data) {
        data.setUser(System.getenv("username"));
        data.setPassword(System.getenv("password"));
        data.setServerName(System.getenv("servername"));
        data.setDatabaseName(System.getenv("databasename"));
    }

    private static void setDS(SQLServerDataSource data, String[] info) {
        data.setUser(info[0]);
        data.setPassword(info[1]);
        data.setServerName(info[2]);
        data.setDatabaseName(info[3]);
    }

    /**
     * Establishes the database connection
     *
     * @param sqlServerDataSource DataSource with all the necessary information to get the connection
     * @return the connection
     */
    public static Connection beginConnection(SQLServerDataSource sqlServerDataSource) {
        LOGGER.info("Establishing database connection...");
        try {
            return sqlServerDataSource.getConnection();
        } catch (SQLServerException e) {
            LOGGER.info("Unable to connect. Please try again." + e);
        }
        return null;
    }

    /**
     * Closes the connection to the database
     *
     * @param connection connection that is opened
     * @return true if connection is closed
     */
    public static boolean closeConnection(Connection connection) {
        try {
            if (connection != null)
                connection.close();
            LOGGER.info("Database disconnected.");
            return true;
        } catch (SQLException e) {
            LOGGER.info("Unable to disconnect." + e);
        }
        return false;
    }

    public static UniqueTableDto selectFrom(String entity, SQLServerDataSource source, int skip, int top) throws SQLException {
        Connection connection = beginConnection(source);
        PreparedStatement statementSelect = null;
        ResultSet resultSet;
        UniqueTableDto sqlRows;
        try {
            statementSelect = connection != null ? connection.prepareStatement("select * from " + entity, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE) : null;
            resultSet = statementSelect != null ? statementSelect.executeQuery() : null;
            sqlRows = new UniqueTableDto(resultSet, skip, top);
        } finally {
            if (statementSelect != null) statementSelect.close();
            if (connection != null) closeConnection(connection);
        }
        return sqlRows;
    }

}