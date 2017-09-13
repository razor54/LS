package pt.isel.ls.model.commands;

import pt.isel.ls.Result.*;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.entities.Entity;
import pt.isel.ls.utils.ResultLog;
import pt.isel.ls.view.htmlViews.View;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * Transform a string to a result with a format and destiny.
 */
public class HeaderCollection {
    private static final int NUMBER_OF_HEADERS = 4;
    private static final HashMap<String, BiFunction<Stream<Entity>,View<Entity>, String>> format = new HashMap<>();
    private static final HashMap<String, BinaryOperator<String>> destination = new HashMap<>();
    private static final HashMap<String, HashMap> headerType = new HashMap<>();



    static {
        headerType.put("accept", format);
        headerType.put("file-name", destination);

        format.put("application/json", HeaderCollection::toJson);
        format.put("text/html", HeaderCollection::toHtml);
        format.put("text/plain", HeaderCollection::toPlain);

        destination.put("file-name", HeaderCollection::writeToFile);
        destination.put("console", HeaderCollection::writeToConsole);
    }

    private static String writeToConsole(String output, String noInterest) {
        System.out.println(output);
        return output;
    }

    private static String writeToFile(String output, String filePath) {

        try (FileWriter p = new FileWriter(filePath, true)) {
            p.write(output);
            p.flush();
            return output;
        } catch (IOException e) {
            System.out.println(e.getMessage());
         //   throw new UncheckedIOException (e);
            return null;
        }

    }

    private static String toPlain(Stream<Entity> entityStream, View<Entity> entityView) {
        return entityView.getPlain(entityStream);
    }

    private static String toHtml(Stream<Entity> entityStream, View<Entity> entityView) {
        return entityView.getHtml(entityStream);    }

    private static String toJson(Stream<Entity> entityStream, View<Entity> entityView) {
        return entityView.getJson(entityStream);
    }


    public static String  parseHeaders(HashMap<String, String> values, Stream<Entity> toEntity, View view) {
        String accept = values.getOrDefault("accept", "text/html");
        String formattedValue = format.get(accept).apply(toEntity, view);


        BinaryOperator<String> console = values.get("file-name") == null ?
                destination.get("console") : destination.get("file-name");
        return console.apply(formattedValue,values.get("file-name"));


    }
}
