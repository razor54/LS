package pt.isel.ls.control;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.control.http.contents.HtmlContent;
import pt.isel.ls.control.http.responses.HttpResponse;
import pt.isel.ls.model.commands.HeaderCollection;
import pt.isel.ls.model.commands.ICommand;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.entities.Entity;
import pt.isel.ls.utils.InfoTable;
import pt.isel.ls.view.htmlViews.View;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static pt.isel.ls.control.http.HttpStatusCode.InternalServerError;

public class CommandExecuter {
    private static final Logger LOGGER = Logger.getLogger(CommandExecuter.class.getName());
    private final ICommand cmd;
    private final HashMap<String, String> values;
    private final View entityView;
    private Function<DTO, Stream<Entity>> toEntity;
    private Function<HashMap<String, String>, String> link;
    private String CONFLITED = "conflicted";
    private String TRUNCATED = "truncated";

    public CommandExecuter(ICommand cmd, HashMap<String, String> values, View view) {
        this.cmd = cmd;
        this.values = values;
        this.entityView = view;
    }


    public CommandExecuter(ICommand cmd, HashMap<String, String> values, View view, Function<DTO, Stream<Entity>> toEntity) {
        this.cmd = cmd;
        this.values = values;
        this.toEntity = toEntity;
        this.entityView = view;
    }

    public CommandExecuter(ICommand cmd, HashMap<String, String> values, View view, Function<DTO, Stream<Entity>> toEntity, Function<HashMap<String, String>, String> link) {
        this.cmd = cmd;
        this.values = values;
        this.toEntity = toEntity;
        this.entityView = view;
        this.link = link;
    }

    /**
     * Returns an httpResponse with the content of the process command.
     * <p>
     * Commands may product a data, a message or a static information.
     * <p>
     *
     *
     * @param src Sql connection to database
     * @return Creates a response with the content of the executed command.
     */
    public HttpResponse act(SQLServerDataSource src) {
        DTO data = null;
        try {
            data = cmd.execute(src, values);
            HttpStatusCode statusCode = data.getStatusCode();

            switch (data.getType()) {
                case Data:  //GET METHODS
                    return processGet(data, statusCode);
                case Info:  //Static information, like index
                    return processStaticInfo(data, statusCode);
                case Message: // POST METHODS ->> need to support user bad inputs
                    return processPutOrPost(data, statusCode);
                default:
                    LOGGER.info("Couldn't execute command: no valid data type");
                    return new HttpResponse(InternalServerError, View.load("public/views/InvalidValue.html"));

            }
        } catch (Exception e) {
            LOGGER.info("Couldn't execute command: " + e.getMessage());
            return new HttpResponse(InternalServerError, View.load("public/views/InvalidValue.html"));
        }
    }

    /**
     * method that will process a put or post command
     * @param data  raw data
     * @param statusCode    status of the last operation
     * @return  An http response with the the path of the the next page
     */
    private HttpResponse processPutOrPost(DTO data, HttpStatusCode statusCode) {
        if (statusCode.isBadUsage()) {
            LOGGER.info("Command failed: checking causes...");
            return checkErrorType(data);
        }
        LOGGER.info("Command executed.");
        return new HttpResponse(statusCode, link.apply(this.values));
    }

    private HttpResponse checkErrorType(DTO data) {
        String message = data.getMessage();
        if(message.contains(CONFLITED)) {
            return new HttpResponse(HttpStatusCode.BadRequest, View.load("public/views/NoSuchInput.html", getWrongKey(message)));
        } else if(message.contains(TRUNCATED))
            return new HttpResponse(HttpStatusCode.BadRequest, View.load("public/views/TruncatedInput.html",
                    TRUNCATED));

        return new HttpResponse(HttpStatusCode.BadRequest, View.load("public/views/IncorrectInput.html",
                getKeyByValue(values, message)));

//        String error = getKeyByValue(values, message);
//        values.replace(error, "");
//        String[] valuesToFormat = (String[]) values.values().toArray();
//        return new HttpResponse(HttpStatusCode.BadRequest, View.load(data.getViewPath().concat("AfterError"), valuesToFormat));


    }

    private String getWrongKey(String message) {
        return message.substring(message.indexOf("'") + 1, message.lastIndexOf("'"));
    }

    private String getKeyByValue(HashMap<String, String> values, String message) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (entry.getValue().equals(message.substring(message.indexOf("\"") + 1, message.lastIndexOf("\"")))) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * It shall be called when a dto with the status static info is created.
     * Loads a file from a path.
     * @param data  dto with the information to the file path
     * @param statusCode    code of the last operation
     * @return  An http response with the content of the path file
     */
    private HttpResponse processStaticInfo(DTO data, HttpStatusCode statusCode) {
        LOGGER.info("Command executed.");
        return new HttpResponse(statusCode, View.load(data.getViewPath()));
    }

    /**
     * Processes a get command following a layer based operation
     * DTO -> ENTITY -> VIEW -> HTTPResponse
     * @param data  raw informatioon of the page to transform
     * @param statusCode    code of the last operation performed
     * @return  and http response with the content of the transformed dto
     */
    private HttpResponse processGet(DTO data, HttpStatusCode statusCode) {
        entityView.setInfoTable(new InfoTable(values, data.maxRowNumber()));
        Stream<Entity> entities = toEntity.apply(data);
        String view = HeaderCollection.parseHeaders(values, entities, entityView);
        LOGGER.info("Command executed.");
        return new HttpResponse(statusCode, new HtmlContent(view));
    }
}
