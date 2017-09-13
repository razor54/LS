package pt.isel.ls.commands.get;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.control.http.responses.HttpResponse;

import java.io.IOException;
import java.sql.SQLException;

import static pt.isel.ls.control.CommandManager.findCommand;

public class GetStudentWithNumberTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void GetStudentWithNumber() throws SQLException, IOException {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);

        HttpResponse IResult = null;
        String expected = "Student{email='jose@sa.pt', pid='LEIC', number=1231, name='Jose Sa', classes=[Klass{courceAcronym='LS', semester='1617v', id='d1', teachers=[]}]}";

        // Act
        CommandExecuter executer = findCommand("GET /students/1231  accept:text/plain");
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);


        // Assert
        Assert.assertEquals(expected, IResult.getInfo().trim());
    }

}