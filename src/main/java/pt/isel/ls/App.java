package pt.isel.ls;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.control.CommandExecuter;
import pt.isel.ls.control.CommandManager;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.control.http.responses.HttpResponse;
import pt.isel.ls.model.exceptions.CommandNotFoundException;
import pt.isel.ls.view.htmlViews.View;

import java.util.Scanner;

//TO RUN = > java -classpath "vendor\main\*;build\classes\main" pt.isel.ls.App
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());
    public static final String INVALID_FILE_HTML = "public/views/InvalidCommand.html";
    private static String LISTEN = "LISTEN /";

    public static void main(String[] args) {
        LOGGER.info("Starting application...");
//        runServer();
        runLocal(args);
    }

    private static void runServer() {
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource();
        findAndAct(sqlServerDataSource, LISTEN);

    }

    private static void runLocal(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource("CREDENTIALS");
        if (args.length != 0)
            readAndAct(args, sqlServerDataSource);
        else
            readAndActWhile(scanner, sqlServerDataSource);
    }

    // Local
    private static void readAndActWhile(Scanner scanner, SQLServerDataSource sqlServerDataSource) {
        while (true) {
            String input = scanner.nextLine();
            findAndAct(sqlServerDataSource, input);
            if (input.contains("EXIT /"))
                return;
        }
    }

    private static void readAndAct(String[] sc, SQLServerDataSource sqlServerDataSource) {
        StringBuilder input = new StringBuilder();

        for (String s : sc) {
            input.append(s).append(" ");
        }

        findAndAct(sqlServerDataSource, input.toString());
    }

    public static HttpResponse findAndAct(SQLServerDataSource sqlServerDataSource, String input) {
        try {
            CommandExecuter command = CommandManager.findCommand(input);
            return command.act(sqlServerDataSource);
        } catch (CommandNotFoundException e) {

            LOGGER.error("Error: " + e.getMessage());
            return new HttpResponse(HttpStatusCode.BadRequest, View.load("public/views/InvalidCommand.html"));
        }
    }


    public static HttpResponse findAndAct(SQLServerDataSource sqlServerDataSource, String method, String path, String query) {
        try {
            if(path.contains("css"))
                return new HttpResponse(HttpStatusCode.Ok,View.locadCss(path.substring(1)));    // no command for css

            String input = method +" " + path +
                    (( query != null) ? " " + query : "");  // url might not have a query
            CommandExecuter command = CommandManager.findCommand(input);
            return command.act(sqlServerDataSource);
        } catch (CommandNotFoundException e) {
            LOGGER.error("Error: " + e.getMessage());
            return new HttpResponse(HttpStatusCode.BadRequest, View.load(INVALID_FILE_HTML));
        }
    }
}




