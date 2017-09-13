package pt.isel.ls.commands.post;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.model.commands.post.PostProgrammes;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.model.data.dtos.DTO;

import java.sql.SQLException;
import java.util.HashMap;

import static pt.isel.ls.Arrange.viewTable;


public class PostProgrammesTest {

    private static final String ENTITY = "Programmes";
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";
    private static String baseCommand = "POST /programmes pid=leim&name=lic.+eng.+inf.+multi&length=6";

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
        PostProgrammes cmd = new PostProgrammes();
        DTO result = null;
        HashMap<String, String> map = Arrange.createMap();  //set the hashmap
        map.put("pid", "leim");
        map.put("name", "lic. eng. inf. multi");
        map.put("length", "6");
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