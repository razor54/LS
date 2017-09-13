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


public class GetAllTeachersTest {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getAllTEachersTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected = "[{\"Email\" : \"Filipes@sa.pt\",\"Name\" : \"Filipe Sousa\",\"Number\" : \"12\"}\n" +
                "{\"Email\" : \"JoaoRocha@sa.pt\",\"Name\" : \"Joao Rocha\",\"Number\" : \"13\"}\n" +
                "{\"Email\" : \"luisp@sa.pt\",\"Name\" : \"Luis Pedro\",\"Number\" : \"14\"}\n" +
                "]";

        CommandExecuter executer = findCommand("GET /teachers accept:application/json");
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);

        // Assert
        Assert.assertEquals(expected, IResult.getInfo());
    }

}
