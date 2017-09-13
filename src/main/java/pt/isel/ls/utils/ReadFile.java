package pt.isel.ls.utils;

import java.io.InputStream;
import java.util.Scanner;


public class ReadFile {

    public static String readFromFile(String path)  {
        InputStream in = ClassLoader.getSystemResourceAsStream(path);

        Scanner sc = new Scanner(in);
        StringBuilder builder = new StringBuilder();
        while (sc.hasNext()) {
            builder.append(sc.nextLine()).append("\n");
        }
        return builder.toString();

    }
}
