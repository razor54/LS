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

    public class GetClassTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void testGetAllFromCourseOnSemesterWithNumber() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected =
                "Klass{courceAcronym='LS', semester='1617v', id='d1', teachers=[Teacher{number=12, email='Filipes@sa.pt', name='Filipe Sousa', classes=null}]}\n";
        // Act

        CommandExecuter command = findCommand("GET /courses/LS/classes/1617v/d1 accept:text/plain");
        Assert.assertNotNull(command);
        IResult = command.act(sqlServerDataSource);

        // Assert

        Assert.assertEquals(expected.trim(), IResult.getInfo().trim());
    }


}