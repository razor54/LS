package pt.isel.ls.commands.put;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.http.responses.HttpResponse;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.model.commands.put.PutTeachers;
import pt.isel.ls.control.JDBCConnection;

import static pt.isel.ls.control.CommandManager.findCommand;


public class PutTeachersTest {

    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void putTeacherTest() {

        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        PutTeachers putTeachers = new PutTeachers();
        HttpResponse iResult = null;
        CommandExecuter executer = findCommand("PUT /teachers/12 name=Pedro+Felipe&email=pedrofelipe@isel.pt");
        Assert.assertNotNull(executer);
        iResult = executer.act(sqlServerDataSource);

        Assert.assertNotNull(iResult);
        Assert.assertEquals(HttpStatusCode.Ok, iResult.getStatusCode());
    }
}
