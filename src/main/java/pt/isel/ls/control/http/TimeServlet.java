package pt.isel.ls.control.http;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.App;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.control.http.responses.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.stream.Collectors;

public class TimeServlet extends HttpServlet {

    private static final Logger _logger = LoggerFactory.getLogger(TimeServlet.class);
    // Local
    private final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource("CREDENTIALS");
    // Server
//    private final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        _logger.info("{} on '{}' with accept:'{}'", req.getMethod(), req.getRequestURI(), req.getHeader("Accept"));
        // _logger.warn("no, its not a warn");

        URL url = new URL(getRelativeUrl(req));

        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(test);

        HttpResponse str;

        //str = App.findAndAct(sqlServerDataSource, command);
        str = App.findAndAct(sqlServerDataSource, req.getMethod(), url.getPath(), url.getQuery());
        str.send(resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _logger.info("{} on '{}' with accept:'{}'", req.getMethod(), req.getRequestURI(), req.getHeader("Accept"));
        // _logger.warn("no, its not a warn");

//        URL url = new URL(getRelativeUrl(req));

        HttpResponse str;
        Enumeration<String> param = req.getParameterNames();
        StringBuilder parameters = new StringBuilder();
        while (param.hasMoreElements()) {
            String nextElem = param.nextElement();
            parameters.append(nextElem).append("=").append(req.getParameter(nextElem).replace(" ", "+")).append("&");

        }
        parameters.deleteCharAt(parameters.length() - 1);
        String path = "POST " + req.getPathInfo() + " " + parameters;
        str = App.findAndAct(sqlServerDataSource, path);

        str.send(resp);
    }

    private static String getRelativeUrl(
            HttpServletRequest request) {

        StringBuffer buf = request.getRequestURL();

        if (request.getQueryString() != null) {
            buf.append("?");
            buf.append(request.getQueryString());
        }

        return buf.toString();
    }
}
