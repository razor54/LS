package pt.isel.ls.commands.get;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.control.http.responses.HttpResponse;

import static pt.isel.ls.control.CommandManager.findCommand;


public class GetClassesForTeacherWithNumberTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getClassesForTeacherWithNumberTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected = "{\"Course\" : \"LS\",\"Semester\" : \"1617v\",\"Id\" : \"d2\"\"Teachers\" : \"[]\"}\n" +
                "{\"Course\" : \"MPD\",\"Semester\" : \"1617v\",\"Id\" : \"d2\"\"Teachers\" : \"[]\"}\n";
        // Act
        CommandExecuter executer = findCommand("GET /teachers/13/classes accept:application/json");
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);

        // Assert
        Assert.assertEquals(expected, IResult.getInfo());
    }


}