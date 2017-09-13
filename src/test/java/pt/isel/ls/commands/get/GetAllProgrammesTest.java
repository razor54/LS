package pt.isel.ls.commands.get;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.control.http.responses.HttpResponse;

import java.util.HashMap;

import static pt.isel.ls.control.CommandManager.findCommand;


public class GetAllProgrammesTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getAllProgrammesTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected = "[{\"Programme Id\" : \"LEIC\",\"Name\" : \"Lic.Eng.Inf.Comp.\",\"Length\" : \"6\"}\n" +
                "]";
        HashMap<String, String> map;
        // Act
        CommandExecuter executer = findCommand("GET /programmes accept:application/json");
        Assert.assertNotNull(executer);

        IResult = executer.act(sqlServerDataSource);


        // Assert
        Assert.assertEquals(expected, IResult.getInfo());

    }

}
