package pt.isel.ls.commands.post;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.model.commands.post.PostTeachers;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.model.data.dtos.DTO;

import java.sql.SQLException;
import java.util.HashMap;

import static pt.isel.ls.Arrange.viewTable;


public class PostTeachersTest {

    private static final String ENTITY = "Teachers";
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";
    private static String baseCommand = "POST /teachers num=1207&name=Pedro+Felix&email=pedrofelix@isel.pt";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void testPostingAValidTeacher() {
        final SQLServerDataSource source = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
               /*
         * Arrange
         */
        int initialRows = viewTable(source, ENTITY);         //view initial rows
        PostTeachers cmd = new PostTeachers();
        DTO result = null;
        HashMap<String, String> map = Arrange.createMap();  //set the hashmap
        map.put("num", "1207");
        map.put("name", "Pedro Felix");
        map.put("email", "pedrofelix@isel.pt");
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