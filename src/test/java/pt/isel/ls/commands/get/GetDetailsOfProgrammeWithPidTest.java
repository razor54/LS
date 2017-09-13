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


public class GetDetailsOfProgrammeWithPidTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getDetailsOFProgrammeTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected = "Programme{programID='LEIC', length=6, name='Lic.Eng.Inf.Comp.', courses=[LS, MPD]}\n";

        // Act
        CommandExecuter executer = findCommand("GET /programmes/leic accept:text/plain");
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);

        // Assert
        Assert.assertNotNull(IResult);
        Assert.assertEquals(expected, IResult.getInfo());
    }


}