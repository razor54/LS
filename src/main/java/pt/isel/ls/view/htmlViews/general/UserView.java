package pt.isel.ls.view.htmlViews.general;


import pt.isel.ls.model.data.entities.Users;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.view.htmlViews.View;

import java.util.stream.Stream;

public class UserView extends View<Users> {

    private final String filePath;
    private static final String htmlFile = "public/views/Users.html";

    protected UserView(String filePath) {
        this.filePath = filePath;
    }

    public UserView() {
        this.filePath = htmlFile;
    }

    @Override
    public String getHtml(Stream<Users> entities) {
        return String.format(readHtmlFromFIle(filePath),
                _formatter.formatHtml(translateStream(entities),1),
                buildPaging(infoTable));

    }

    @Override
    protected String getEntityHtml(Users s) {
        HTMLTr<String> line = contructGenericRow(s);
        // extra info
        return line.getName();
    }

    protected HTMLTr<String> contructGenericRow(Users t) {
        HTMLTr<String> row = new HTMLTr<>();
        //int number = t.getNumber();
        String type = t.getType().toLowerCase();
        String path = type.equals("s") ? "/students" : "/teachers";
        return row
                .addData(t.getEmail())
                .addData(t.getName())
                .addData(t.getType(), path)
                .addData(t.getNumber() + "", path + "/" + t.getNumber());
        //.addData(valueOf(number), "/students/" + number);
    }


}
