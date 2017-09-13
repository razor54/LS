package pt.isel.ls.utils;

import java.util.function.BiFunction;

public class Template {
    private final String variableWordIndicator;
    private final String[] possibleVariableWords;
    private final String expected;
    private final BiFunction<Template, String, Boolean> template0f;


    public Template(BiFunction<Template, String, Boolean> template0f, String expected, String variableWordIndicator, String[] variablePossibilities) {
        this.variableWordIndicator = variableWordIndicator;
        this.possibleVariableWords = variablePossibilities;
        this.expected = expected;
        this.template0f = template0f;
    }

    public String getVariableWordIndicator() {
        return variableWordIndicator;
    }

    public String[] getPossibleVariableWords() {
        return possibleVariableWords;
    }

    public String getExpected() {
        return expected;
    }

    public boolean isTemplateOf(String command) {
        return template0f.apply(this, command);
    }

    public static  Template of(BiFunction<Template, String, Boolean> mapper, String expected, String variableWordIndicator, String[] variablePossibilities) {
        return new Template(mapper, expected, variableWordIndicator, variablePossibilities);
    }

    public static  Template of(BiFunction<Template, String, Boolean> mapper, String expected, String variableWordIndicator) {
        return new Template(mapper, expected, variableWordIndicator, null);
    }


}
