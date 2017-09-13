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


public class GetAllCoursesTests {
    private static final String SQL_DATABASE_ENVIRONMENT_VARIABLE = "TestBase";

    @BeforeClass
    public static void beforeClass() {
        Arrange.beforeTest();

    }

    @Test
    public void getAllCoursesTest() {
        // Arrange
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource(SQL_DATABASE_ENVIRONMENT_VARIABLE);
        HttpResponse IResult = null;
        String expected ="<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "    <title>Courses</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/public/css/head.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Courses</h1>\n" +
                "<hr>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th>Mandatory</th>\n" +
                "        <th>Name</th>\n" +
                "        <th>Acronym</th>\n" +
                "        <th>Coordinator</th>\n" +
                "    </tr>\n" +
                "    \n" +
                "\t<tr>\n" +
                "\t\t<td>\n" +
                "\t\ttrue\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\tLabSoft\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\t\t<a href=/courses/LS>\n" +
                "\t\t\tLS\n" +
                "\t\t\t</a>\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\t\t<a href=/teachers/12>\n" +
                "\t\t\t12\n" +
                "\t\t\t</a>\n" +
                "\t\t</td>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td>\n" +
                "\t\tfalse\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\tMod. Padrao Dados\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\t\t<a href=/courses/MPD>\n" +
                "\t\t\tMPD\n" +
                "\t\t\t</a>\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\t\t<a href=/teachers/14>\n" +
                "\t\t\t14\n" +
                "\t\t\t</a>\n" +
                "\t\t</td>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td>\n" +
                "\t\tfalse\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\tSist.Multi.\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\t\t<a href=/courses/SM>\n" +
                "\t\t\tSM\n" +
                "\t\t\t</a>\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\t\t<a href=/teachers/12>\n" +
                "\t\t\t12\n" +
                "\t\t\t</a>\n" +
                "\t\t</td>\n" +
                "\t</tr>\n" +
                "</table>\n" +
                "\n" +
                "<form action=\"/courses\" method=\"post\">\n" +
                "    Coordinator:<br>\n" +
                "    <input type=\"number\" name=\"teacher\" required=\"required\"> <br>\n" +
                "    Acronym:<br>\n" +
                "    <input type=\"text\" name=\"acr\" required=\"required\"> <br>\n" +
                "    Name:<br>\n" +
                "    <input type=\"text\" name=\"name\" required=\"required\"> <br><br>\n" +
                "    <input type=\"submit\" value=\"Submit\">\n" +
                "</form>\n" +
                "<br>\n" +
                "\n" +
                "<a href = \"/\" class=\"button\"> Index </a>\n" +
                "</body>\n" +
                "</html>";

        // Act
        CommandExecuter executer = findCommand("GET /courses accept:text/html" );
        Assert.assertNotNull(executer);
        IResult = executer.act(sqlServerDataSource);


        // Assert
        Assert.assertEquals(expected.trim(), IResult.getInfo().trim());

    }

}
