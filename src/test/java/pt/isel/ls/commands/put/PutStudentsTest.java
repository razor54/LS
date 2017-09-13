package pt.isel.ls.commands.put;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.http.responses.HttpResponse;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.control.JDBCConnection;

import static pt.isel.ls.control.CommandManager.findCommand;


public class PutStudentsTest {

    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void putStudentTest() {

        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse iResult = null;
        CommandExecuter executer = findCommand("PUT /students/1221 name=Hannibal+Sousa&email=hannibal.sousa@isel.pt");
        Assert.assertNotNull(executer);
        iResult = executer.act(sqlServerDataSource);

        Assert.assertNotNull(iResult);
        Assert.assertEquals(HttpStatusCode.Ok,iResult.getStatusCode() );
    }
}
