package pt.isel.ls.utils;

import java.util.LinkedList;
import java.util.List;

public class ExceptionLog {

    private static List<Exception> exceptions = new LinkedList<>();

    public static void addException(Exception exception) {
        exceptions.add(exception);
    }

    public static List<Exception> getExceptions() {
        return exceptions;
    }
}
