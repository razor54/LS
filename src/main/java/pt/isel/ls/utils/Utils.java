package pt.isel.ls.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Utils {

    /**
     * Analyzes the ResultSet and converts it to a String
     *
     * @param rs ResultSet to analyze
     * @return a String that matches the ResultSet information
     * @throws SQLException throws SQLException above
     */
    public static String printResultSet(ResultSet rs) throws SQLException {
        String result = "";

        ResultSetMetaData rsMetaData = rs.getMetaData();
        int columnNumber = rsMetaData.getColumnCount();
        String columnName[] = new String[columnNumber];
        for (int i = 0; i < columnNumber; i++) {
            columnName[i] = rsMetaData.getColumnName(i + 1);
        }
        while (rs.next()) {
            for (int i = 0; i < columnNumber; i++) {
                if (i > 0)
                    result += ", ";
                if (columnName[i] != null)
                    result += columnName[i] + " " + rs.getObject(i + 1);
            }
            result += "\n";
        }
        return result;
    }
}
