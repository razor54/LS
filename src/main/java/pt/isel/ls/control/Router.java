package pt.isel.ls.control;

import pt.isel.ls.model.commands.ICommand;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.entities.Entity;
import pt.isel.ls.utils.Template;
import pt.isel.ls.view.htmlViews.View;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class Router<T extends DTO> {
    private final Template template;
    private final ICommand cmd;
    private final BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConverter;
    private final View view;
    private Function<T, Stream<Entity>> dtoConverter;
    private Function<HashMap<String, String>, String> linkCreator;

    private Router(Template template, ICommand cmd, BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConverter, View view) {
        this.template = template;
        this.cmd = cmd;
        this.pathConverter = pathConverter;
        this.view = view;
    }

    private Router(
            Template template,
            ICommand cmd,
            BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConverter,
            Function<HashMap<String, String>, String> linkCreator) {
        this.template = template;
        this.cmd = cmd;
        this.pathConverter = pathConverter;
        this.linkCreator = linkCreator;
        view=null;
    }

    private Router(Template template, ICommand cmd, BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConverter) {
        this.template = template;
        this.cmd = cmd;
        this.pathConverter = pathConverter;
        this.view = null;
    }

    private Router(
            Template template,
            ICommand cmd,
            BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConverter,
            View view,
            Function<T, Stream<Entity>> dtoConverter
    ) {

        this(template, cmd, pathConverter, view);
        this.dtoConverter = dtoConverter;
    }

    public static Router of(Template template, ICommand cmd, BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConversion, View v) {
        return new Router(template, cmd, pathConversion, v);
    }

    public static Router of(
            Template template,
            ICommand cmd,
            BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConversion,
            Function<HashMap<String, String>, String> linkCreator) {
        return new Router(template, cmd, pathConversion, linkCreator);
    }


    public static Router of(Template template, ICommand cmd, BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConverter) {
        return new Router(template, cmd, pathConverter);
    }

    public static <T extends DTO> Router of(
            Template template,
            ICommand cmd,
            BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathConverter,
            View v,
            Function<T, Stream<Entity>> dtoConverter
    ) {
        return new Router<>(template, cmd, pathConverter, v, dtoConverter);
    }

    // CLASS GETTERS
    public Function<T, Stream<Entity>> getDtoConverter() {
        return dtoConverter;
    }

    public View getView() {
        return view;
    }

    public Template getTemplate() {
        return template;
    }

    public ICommand getCmd() {
        return cmd;
    }

    public BiFunction<String[], HashMap<String, String>, HashMap<String, String>> getPathConverter() {
        return pathConverter;
    }

    public Function<HashMap<String, String>, String> getLinkCreator() {
        return linkCreator;
    }
}
