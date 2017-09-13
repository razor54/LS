package pt.isel.ls.control.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class FirstHttpServer {

    /* 
     * TCP port where to listen. 
     * Standard port for HTTP is 80 but might be already in use
     */
//    private static final int LISTEN_PORT = 7777;
    private static final int LISTEN_PORT = 8080;

    public void startServer() throws Exception {

        System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");

        Logger logger = LoggerFactory.getLogger(FirstHttpServer.class);
        logger.info("Starting main...");

        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        String resPath = ClassLoader.getSystemResource("public").toString();
        holderHome.setInitParameter("resourceBase", resPath);
        holderHome.setInitParameter("dirAllowed", "true");
        holderHome.setInitParameter("pathInfoOnly", "true");

        String portDef = System.getenv("PORT");
        int port = portDef != null ? Integer.valueOf(portDef) : LISTEN_PORT;
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler();
        context.addServlet(new ServletHolder(new TimeServlet()),"/*");

        server.setHandler(context);
        server.start();
        logger.info("Server started");
        server.join();

        logger.info("main ends.");
    }
}
