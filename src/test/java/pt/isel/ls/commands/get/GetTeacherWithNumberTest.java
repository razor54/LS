package pt.isel.ls.commands.get;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.control.http.responses.HttpResponse;
import pt.isel.ls.model.commands.get.element.GetTeacher;
import pt.isel.ls.control.JDBCConnection;

import static pt.isel.ls.control.CommandManager.findCommand;

public class GetTeacherWithNumberTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void testGetTeacherWithNumber() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        GetTeacher cmd = new GetTeacher();
        HttpResponse IResult = null;
        String expected = "Teacher{number=12, email='Filipes@sa.pt', name='Filipe Sousa', classes=[Klass{courceAcronym='LS', semester='1617v', id='d1', teachers=[]}]}";

        // Act
        CommandExecuter executer = findCommand("GET /teachers/12  accept:text/plain");
        Assert.assertNotNull(executer);

        IResult = executer.act(sqlServerDataSource);

        // Assert
        Assert.assertEquals(expected, IResult.getInfo().trim());
    }


}