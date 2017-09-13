package pt.isel.ls.commands.delete;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.isel.ls.Arrange;
import pt.isel.ls.control.http.responses.HttpResponse;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.control.JDBCConnection;

import static pt.isel.ls.Arrange.viewTable;
import static pt.isel.ls.control.CommandManager.findCommand;

public class DeleteStudentFomClassTest {

    public static final String ENTITY = "student_class_assoc";
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";
    private static String baseCommand = "POST /courses/mpd/classes sem=1617v&num=d1";


    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void deleteStudentFromClassTest() {
        final SQLServerDataSource source = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        int initialRows = viewTable(source, ENTITY);
        String com = "DELETE /courses/ls/classes/1617v/d1/students/1231";
        HttpResponse iresult = null;
        CommandExecuter executer = findCommand(com);
        iresult = executer.act(source);


        Assert.assertNotNull(iresult);
        Assert.assertEquals(HttpStatusCode.Ok,iresult.getStatusCode());
        int actualRows = viewTable(source, ENTITY);
        Assert.assertTrue(initialRows > actualRows);
    }
}
