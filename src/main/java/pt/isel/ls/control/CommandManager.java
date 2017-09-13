package pt.isel.ls.control;

import pt.isel.ls.model.commands.AbstractCommand;
import pt.isel.ls.model.commands.CommandTemplate;
import pt.isel.ls.model.commands.delete.AbstractDelete;
import pt.isel.ls.model.commands.delete.DeleteStudentFromClass;
import pt.isel.ls.model.commands.exit.Exit;
import pt.isel.ls.model.commands.get.element.*;
import pt.isel.ls.model.commands.get.list.GetAllUsers;
import pt.isel.ls.model.commands.get.list.classes.GetAllClasses;
import pt.isel.ls.model.commands.get.list.classes.GetCourseClasses;
import pt.isel.ls.model.commands.get.list.classes.GetSemClasses;
import pt.isel.ls.model.commands.get.list.courses.GetAllCourses;
import pt.isel.ls.model.commands.get.list.courses.GetProgrammeStructure;
import pt.isel.ls.model.commands.get.list.programmes.GetAllProgrammes;
import pt.isel.ls.model.commands.get.list.students.GetAllStudents;
import pt.isel.ls.model.commands.get.list.students.GetClassStudents;
import pt.isel.ls.model.commands.get.list.students.GetClassStudentsAscendingNr;
import pt.isel.ls.model.commands.get.list.teachers.GetAllTeachers;
import pt.isel.ls.model.commands.get.list.teachers.GetTeacherClasses;
import pt.isel.ls.model.commands.get.list.teachers.GetTeachersForClass;
import pt.isel.ls.model.commands.listen.Listen;
import pt.isel.ls.model.commands.options.Options;
import pt.isel.ls.model.commands.post.*;
import pt.isel.ls.model.commands.put.AbstractPut;
import pt.isel.ls.model.commands.put.PutStudents;
import pt.isel.ls.model.commands.put.PutTeachers;
import pt.isel.ls.model.commands.root.GetAll;
import pt.isel.ls.model.data.dtos.DTO;
import pt.isel.ls.model.data.entities.*;
import pt.isel.ls.model.exceptions.CommandNotFoundException;
import pt.isel.ls.utils.Template;
import pt.isel.ls.view.htmlViews.View;
import pt.isel.ls.view.htmlViews.general.*;
import pt.isel.ls.view.htmlViews.specific.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static pt.isel.ls.model.commands.AbstractCommand.*;

public class CommandManager {
    private static final String VARIABLE_OPTION_TOKEN = "{";
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class.getName());
    private static ArrayList<Router> routers = new ArrayList<>();
    private static BiFunction<Template, String, Boolean> isTemplateOf;

    static {
        isTemplateOf = CommandTemplate::isTemplateOf;
        //specific
        routers.add(routerOf(new GetCourse(), new SCourseView(), Course::createOneCourse));
        routers.add(routerOf(new GetClass(), new SClassView(), Klass::createOneClass));
        routers.add(routerOf(new GetTeacher(), new STeacherView(), Teacher::createOneTeacher));
        routers.add(routerOf(new GetProgramme(), new SProgrammeView(), Programme::createOneProgramme));
        routers.add(routerOf(new GetStudent(), new SStudentView(), Student::createOneStudent));

        //generics
        routers.add(routerOf(new GetAll()));
        routers.add(routerOf(new GetAllStudents(), new StudentView(), Student::createStudentStream));
        routers.add(routerOf(new GetAllTeachers(), new TeacherView(), Teacher::creteTeacherStream));
        routers.add(routerOf(new GetAllClasses(), new ClassView(), Klass::createClassForCourses));
        routers.add(routerOf(new GetAllCourses(), new CourseView(), Course::creteateCourseStream));
        routers.add(routerOf(new GetAllProgrammes(), new ProgrammeView(), Programme::createProgrammeStream));
        routers.add(routerOf(new GetCourseClasses(), new CourseClassView(), Klass::createClassForCourses));
        routers.add(routerOf(new GetSemClasses(), new ClassView(), Klass::createClassForCourses));
        routers.add(routerOf(new GetProgrammeStructure(), new ProgrammeCoursesView(), Course::creteateCourseStream));
        routers.add(routerOf(new GetClassStudentsAscendingNr(), new StudentView(), Student::createStudentStream));
        routers.add(routerOf(new GetTeacherClasses(), new ClassView(), Klass::createClass));
        routers.add(routerOf(new GetTeachersForClass(), new TeacherView(), Teacher::creteTeacherForClassesStream));
        routers.add(routerOf(new GetClassStudents(), new StudentView(), Student::createStudentStream));
        routers.add(routerOf(new PostStudents()));
        routers.add(routerOf(new PostTeachers()));
        routers.add(routerOf(new PostTeacherToClass()));
        routers.add(routerOf(new PostProgrammes()));
        routers.add(routerOf(new PostNewCourseToProgramme()));
        routers.add(routerOf(new PostClassOnCourse()));
        routers.add(routerOf(new PostStudentToClass()));
        routers.add(routerOf(new PostCourses()));
        routers.add(routerOf(new DeleteStudentFromClass()));
        routers.add(routerOf(new PutStudents()));
        routers.add(routerOf(new PutTeachers()));
        routers.add(routerOf(new Exit()));
        routers.add(routerOf(new Options()));
        routers.add(routerOf(new Listen()));
        routers.add(routerOf(new GetAllUsers(), new UserView(), Users::createUsersStream));
        LOGGER.info("All commands were added.");
    }

    private static Router routerOf(AbstractCommand cmd) {
        return Router.of(Template.of(isTemplateOf, cmd.getBaseCommand(), VARIABLE_OPTION_TOKEN), cmd, cmd::pathConverter);
    }

    private static Router routerOf(AbstractPost cmd) {
        return Router.of(Template.of(isTemplateOf, cmd.getBaseCommand(), VARIABLE_OPTION_TOKEN), cmd, cmd::pathConverter, cmd.getDirectioner());
    }

    private static Router routerOf(AbstractPut cmd) {
        return Router.of(Template.of(isTemplateOf, cmd.getBaseCommand(), VARIABLE_OPTION_TOKEN), cmd, cmd::pathConverter, cmd.getDirectioner());
    }

    private static Router routerOf(AbstractDelete cmd) {
        return Router.of(Template.of(isTemplateOf, cmd.getBaseCommand(), VARIABLE_OPTION_TOKEN), cmd, cmd::pathConverter, cmd.getLinkCreator());
    }

    private static Router routerOf(AbstractCommand cmd, View v) {
        return Router.of(Template.of(isTemplateOf, cmd.getBaseCommand(), VARIABLE_OPTION_TOKEN), cmd, cmd::pathConverter, v);
    }


    private static Router routerOf(AbstractCommand<DTO> cmd, View<? extends Entity> v, Function<? extends DTO, Stream<Entity>> dtoConverter) {
        return Router.of(Template.of(isTemplateOf, cmd.getBaseCommand(), VARIABLE_OPTION_TOKEN), cmd, cmd::pathConverter, v, dtoConverter);
    }


    /**
     * Searches through all the commands and finds the correct one, asked by the user
     *
     * @param command command entered by the user
     * @return the desired ICommand
     */
    public static CommandExecuter findCommand(String command) {
        LOGGER.info("Searching for command...");
        for (Router<DTO> r : routers) {
            Template template = r.getTemplate();
            if (template.isTemplateOf(command)) {
                LOGGER.fine("Command found.");
                return new CommandExecuter(r.getCmd(), separateParametersOf(command, r.getPathConverter()), r.getView(), r.getDtoConverter(), r.getLinkCreator());
            }
        }
        LOGGER.info("Invalid command.");
        throw new CommandNotFoundException("Failed to find command.");
    }

    /**
     * Separates the command method, path and parameters / headers
     *
     * @param input   command found
     * @param pathMap map to write the path
     * @return pathMap with the path
     */
    private static HashMap<String, String> separateParametersOf(String input, BiFunction<String[], HashMap<String, String>, HashMap<String, String>> pathMap) {
        String[] cmdSplit = input.split(COMMAND_SEPARATOR_CHAR);
        HashMap<String, String> commandMap = new HashMap<>();
        commandMap.put("UserInput", cmdSplit[1]);
        // command without path
        if (cmdSplit.length == 1)
            return commandMap;

        // command with parameters or headers
        if (cmdSplit.length == HAS_HEADER_OR_PARAMETERS) {
            commandMap = input.contains(String.valueOf(PARAMETERS_VALUE_SEPARATOR))
                    ?
                    parseParameter(cmdSplit[2], commandMap) : parseHeader(cmdSplit[2], commandMap);
        }

        // command with parameters and headers
        if (cmdSplit.length == HAS_HEADER_AND_PARAMETERS)
            commandMap = parseParameterAndHeader(cmdSplit);

        if (!commandMap.containsKey("skip")) commandMap.put("skip", "0");              //if there is no skip
        if (!commandMap.containsKey("top")) commandMap.put("top", "10");               //if there is no top

        // apply path to commandMap
        return pathMap.apply(cmdSplit[1].split(PATH_SEPARATOR_CHAR), commandMap);
    }

    /**
     * Parse parameters and headers
     *
     * @param afterSplit command already split
     * @return hashmap with parameters parsed
     */
    private static HashMap<String, String> parseParameterAndHeader(String[] afterSplit) {
        HashMap<String, String> map = new HashMap<>();
        map = parseHeader(afterSplit[2], parseHeader(afterSplit[3], map));
        return parseParameter(afterSplit[2], parseParameter(afterSplit[3], map));
    }

    /**
     * Parse parameters
     *
     * @param parameters parameters to parse
     * @param paramHash  map to write to
     * @return hashmap already with parameters
     */
    private static HashMap<String, String> parseParameter(String parameters, HashMap<String, String> paramHash) {
        return createHashMap(parameters, "&", "=", paramHash);
    }

    /**
     * Parse headers
     *
     * @param headers    headers to parse
     * @param headerHash map to write to
     * @return hashmap already with headers
     */
    private static HashMap<String, String> parseHeader(String headers, HashMap<String, String> headerHash) {
        return createHashMap(headers, "\\|", ":", headerHash);
    }

    /**
     * Writes info to hashmap
     *
     * @param toSplit           string to split
     * @param splitter          split accordingly to the splitter
     * @param keyValueSeparator separator
     * @param hashMap           hashmap to write to
     * @return hashmap already written
     */
    private static HashMap<String, String> createHashMap(String toSplit, String splitter, String keyValueSeparator, HashMap<String, String> hashMap) {
        // split accordingly to the splitter
        String[] values = toSplit.split(splitter);
        int numReps = 0;
        // writes values to hashmap
        for (String aMax : values) {
            String[] aux = aMax.split(keyValueSeparator, 2);
            if (aux.length != 2)
                return hashMap;
            String key = aux[0].replace("+", " ");
            if (hashMap.containsKey(key)) key = key + (++numReps);
            hashMap.put(key, aux[1].replace("+", " "));
        }
        return hashMap;
    }


}
