package pt.isel.ls.commands.post;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.model.commands.post.PostStudentToClass;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.model.data.dtos.DTO;

import java.sql.SQLException;
import java.util.HashMap;

import static pt.isel.ls.Arrange.viewTable;


public class TestPostStudentToClass {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";
    private static final String ENTITY = "student_class_assoc";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void PostStudentToClassTest() {
        final SQLServerDataSource source = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
       /*
         * Arrange
         */
        int initialRows = viewTable(source, ENTITY);         //view initial rows
        PostStudentToClass cmd = new PostStudentToClass();
        DTO result = null;
        HashMap<String, String> map = Arrange.createMap();  //set the hashmap
        map.put("acr", "sm");
        map.put("sem", "1617v");
        map.put("classNum", "d1");
        map.put("numStu", "1220");
        map.put("numStu1", "1221");
        /*
         * Act
         */
        try {
            result =  cmd.execute(source, map);
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }

        /*
         *  Assert
         */
        Assert.assertNotNull(result);
        int finalRows = viewTable(source, ENTITY);
        Assert.assertTrue(finalRows > initialRows);

    }
}
