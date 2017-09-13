package pt.isel.ls.commands.post;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.commands.post.PostClassOnCourse;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.SQLException;
import java.util.HashMap;

import static pt.isel.ls.Arrange.viewTable;


public class PostClassOnCourseTest {


    public static final String ENTITY = "Classes";
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";
    private static String baseCommand = "POST /courses/mpd/classes sem=1617v&num=d1";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void testPostingAValidTeacher() {
        final SQLServerDataSource source = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        /*
        *           Arrange
        * */
        UniqueTableDto classes;
        int initialRows = viewTable(source, ENTITY);         //view initial rows
        PostClassOnCourse cmd = new PostClassOnCourse();
        DTO result = null;
        HashMap<String, String> map = Arrange.createMap();  //set the hashmap
        map.put("acr", "mpd");
        map.put("sem", "1617v");
        map.put("num", "d4");
        /*
         *          Act
         */
        try {
            result = cmd.execute(source, map);
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }

        /*
        *            Assert
        * */
        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatusCode.SeeOther, result.getStatusCode());
        int finalRows = viewTable(source, ENTITY);
        Assert.assertTrue(finalRows > initialRows);

    }

}