package pt.isel.ls.model.commands;


import pt.isel.ls.utils.Template;

/**
 * Class whose function is to parse evaluate a command
 */
public class CommandTemplate {

    public static Boolean isTemplateOf(Template template, String input) {

        String[] inputSplit = input.split(AbstractCommand.COMMAND_SEPARATOR_CHAR);
        String[] cmdSplit = template.getExpected().split(AbstractCommand.COMMAND_SEPARATOR_CHAR);

        if (inputSplit.length == 0) // GET /
            return true;

        if (inputSplit.length == 1)     // only method type commands
            return template.getExpected().equals(input);

        if (!inputSplit[AbstractCommand.METHOD].equals(cmdSplit[AbstractCommand.METHOD]))
            return false;

        String[] inputPath = inputSplit[AbstractCommand.PATH].split(AbstractCommand.PATH_SEPARATOR_CHAR);
        String[] expectedPath = cmdSplit[AbstractCommand.PATH].split(AbstractCommand.PATH_SEPARATOR_CHAR);

        if (inputPath.length != expectedPath.length)
            return false;


        return testPath(inputPath, expectedPath, template.getVariableWordIndicator());

    }

    private static boolean testPath(String[] path, String[] expected, String variableWord) {
        for (int i = 1; i < expected.length; i++) {
            if (!path[i].equals(expected[i]) && !expected[i].contains(variableWord))
                return false;
        }
        return true;
    }


    private static boolean testConstantVariables(String[] path, String[] expected, String[] variableWords) {
        for (int i = 1; i < expected.length; i++) {
            if (!path[i].equals(expected[i]) && !containsVariable(path[i], variableWords))
                return false;
        }
        return true;
    }

    private static boolean containsVariable(String s, String[] possibleVariableWords) {
        for (String possibleVariableWord : possibleVariableWords) {
            if (possibleVariableWord.equals(s))
                return true;
        }
        return false;
    }


}

