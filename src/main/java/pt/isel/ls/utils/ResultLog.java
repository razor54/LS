package pt.isel.ls.utils;


import pt.isel.ls.Result.IResult;

import java.util.LinkedList;
import java.util.List;

public class ResultLog {

    private static List<IResult> cache = new LinkedList<>();

    private static IResult lastResult = null;

    public static void addResult(IResult r) {
        lastResult = r;
        cache.add(r);
    }

    public static List<IResult> getResults() {
        return cache;
    }

    public static String getLastResult() {
        return lastResult.getResult().toString();
    }
}
