package pt.isel.ls.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Options {

    private static String fileName = "./src/main/resources/options.txt";

    public static String getOptions() {
        InputStream stream = null;
        StringBuilder toRet = new StringBuilder();
        try {
            stream = new FileInputStream(fileName);
            Scanner scanner = new Scanner(stream);
            while (scanner.hasNextLine()) {
                toRet.append(scanner.nextLine())
                        .append("\n");
            }
            return toRet.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void printOptions() {
        System.out.println(getOptions());

    }

}

