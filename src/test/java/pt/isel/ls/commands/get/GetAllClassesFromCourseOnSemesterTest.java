package pt.isel.ls.commands.get;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.model.commands.get.list.classes.GetSemClasses;
import pt.isel.ls.model.data.dtos.DTO;

import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GetAllClassesFromCourseOnSemesterTest {

    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();
    }

    @Test
    public void testGetAllClassesFromCourseOnSemester() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        GetSemClasses cmd = new GetSemClasses();
        DTO res = null;

        HashMap<String, String> map = Arrange.createMap();
        map.put("acr", "LS");
        map.put("sem", "1617v");


        String expected = "courseacr=LS, semrepresentation=1617v, cid=d1\n" +
                "courseacr=LS, semrepresentation=1617v, cid=d2 ";
        // Act
        try {
            res = cmd.execute(sqlServerDataSource, map);
        } catch (SQLException e) {
            fail(e.getMessage());
        }

        // Assert
        assertNotNull(res);

        assertEquals(expected.trim(), res.getPlainText().trim());
    }

    @Test
    public void test_skip_one_element() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        GetSemClasses cmd = new GetSemClasses();
        DTO res = null;

        HashMap<String, String> map = Arrange.createMap();
        map.put("acr", "LS");
        map.put("sem", "1617v");
        map.replace("skip", "1");

        String expected = "courseacr=LS, semrepresentation=1617v, cid=d2 ";
        // Act
        try {
            res = cmd.execute(sqlServerDataSource, map);
        } catch (SQLException e) {
            fail(e.getMessage());
        }

        // Assert
        assertNotNull(res);

        assertEquals(expected.trim(), res.getPlainText().trim());
    }


}