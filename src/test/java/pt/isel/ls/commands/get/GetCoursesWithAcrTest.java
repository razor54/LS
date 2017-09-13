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


public class GetCoursesWithAcrTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getCoursesWithAcrTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected = "Course{courceAcronym='LS', coordinatorID=12, name='LabSoft', mandatory=true, semesters=[4], programmes=[LEIC]}\n";

        // Act
        CommandExecuter executer = findCommand("GET /courses/ls accept:text/plain");
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);
        // Assert
        Assert.assertEquals(expected, IResult.getInfo());
    }


}