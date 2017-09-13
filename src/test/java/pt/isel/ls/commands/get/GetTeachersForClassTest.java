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


public class GetTeachersForClassTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void testGetTeacherForClass() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected = "Teacher{number=12, email='Filipes@sa.pt', name='Filipe Sousa', classes=null}";

        // Act
        CommandExecuter executer = findCommand("GET /courses/LS/classes/1617v/d1/teachers  accept:text/plain");
        IResult = executer.act(sqlServerDataSource);


        // Assert
        Assert.assertNotNull(IResult);
        Assert.assertEquals(expected, IResult.getInfo().trim());
    }

}