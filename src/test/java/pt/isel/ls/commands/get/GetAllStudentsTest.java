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

import static pt.isel.ls.Arrange.createMap;
import static pt.isel.ls.control.CommandManager.findCommand;


public class GetAllStudentsTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getAllStudentsTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected = "[{\"Email\" : \"JJadams@sa.pt\",\"Name\" : \"JJ Adams\",\"Number\" : \"1220\"\"Programme\" : \"LEIC\"\"Classes\" : \"null\"}\n" +
                "{\"Email\" : \"miguelmartins@sa.pt\",\"Name\" : \"Miguel Martins\",\"Number\" : \"1221\"\"Programme\" : \"LEIC\"\"Classes\" : \"null\"}\n" +
                "{\"Email\" : \"Lucasm@sa.pt\",\"Name\" : \"Lucas M.\",\"Number\" : \"1230\"\"Programme\" : \"LEIC\"\"Classes\" : \"null\"}\n" +
                "{\"Email\" : \"Jose@sa.pt\",\"Name\" : \"Jose Sa\",\"Number\" : \"1231\"\"Programme\" : \"LEIC\"\"Classes\" : \"null\"}\n" +
                "{\"Email\" : \"Manuemanue@sa.pt\",\"Name\" : \"Manuel Ourives\",\"Number\" : \"1232\"\"Programme\" : \"leic\"\"Classes\" : \"null\"}\n" +
                "]";
        HashMap<String, String> map = createMap();
        map.put("all", "students");
        // Act
        CommandExecuter executer = findCommand("GET /students accept:application/json");
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);

        // Assert
        Assert.assertNotNull(IResult);
        Assert.assertEquals(expected, IResult.getInfo());


    }

}
