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


public class GetStudentsForClassTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getStudentsForClassTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);

        HttpResponse IResult = null;
        String expected = "Student{email='jose@sa.pt', pid='LEIC', number=1231, name='Jose Sa', classes=null}\n" +
                "Student{email='Manuemanue@sa.pt', pid='leic', number=1232, name='Manuel Ourives', classes=null}";

        // Act
        CommandExecuter executer = findCommand("GET /courses/ls/classes/1617v/d1/students  accept:text/plain");
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);

        // Assert
        Assert.assertNotNull(IResult);
        Assert.assertEquals(expected, IResult.getInfo().trim());

    }

}
