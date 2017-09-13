package pt.isel.ls.utils;

/**
 * Created by Andre on 29-Jun-17.
 */
public class Formatter {


    public String formatHtml(String html, int initialTabs) {
        initialTabs = initialTabs == 0 ? 0 :initialTabs-1;
        StringBuilder builder = new StringBuilder();
        String temp = html;
        boolean hasElems = true;
        boolean added = true;
        for (int tabs = initialTabs; hasElems; ) {
            String[] split = temp.split(">", 2);

            if (split[0].contains("</")) {
                tabs+= added ? 0 : -1;
                added=false;
            }
            else{
                tabs+= added ? 1: 0;
                added = true;
            }
            String toTab = tabbing(split[0], tabs);
            builder.append(toTab).append(">");

            if (split[1].equals("")) return builder.toString();
            temp = split[1];
        }

        return builder.toString();
    }

    private String tabbing(String s, int tabs) {
        String strTabs = doTabs(tabs);
        StringBuilder builder = new StringBuilder();
        String[] split = s.split("<", 2);
        String trim = split[0].trim();
        if (trim.equals(""))
            return builder
                    .append("\n")
                    .append(strTabs)
                    .append(s.trim()).toString();
        else {
            builder
                    .append("\n")
                    .append(strTabs)
                    .append(trim)
                    .append("\n")
                    .append(strTabs)
                    .append("<")
                    .append(split[1]);

            return builder.toString();
        }
    }

    private String doTabs(int tabs) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < tabs; i++) {
            s.append("\t");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Formatter f = new Formatter();
        String s = "<chao> 234 2343<pao> <arroz> </arroz></pao> </chao>";
        System.out.println(f.formatHtml(s, 1));
    }
}
