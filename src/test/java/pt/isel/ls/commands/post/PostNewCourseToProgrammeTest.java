package pt.isel.ls.commands.post;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.model.commands.post.PostNewCourseToProgramme;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.model.data.dtos.DTO;

import java.sql.SQLException;
import java.util.HashMap;

import static pt.isel.ls.Arrange.viewTable;


public class PostNewCourseToProgrammeTest {

    private static final String ENTITY = "Curricular";
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";
    private static String baseCommand = "POST /programmes/leic/courses acronym=sm&mandatory=false&semesters=3,6";

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
        PostNewCourseToProgramme cmd = new PostNewCourseToProgramme();
        DTO result = null;
        HashMap<String, String> map = Arrange.createMap();  //set the hashmap
        map.put("pid", "leic");
        map.put("acronym", "sm");
        map.put("mandatory", "false");
        map.put("semesters", "3,6");
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