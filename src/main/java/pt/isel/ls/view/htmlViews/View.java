package pt.isel.ls.view.htmlViews;

import pt.isel.ls.control.http.IHttpContent;
import pt.isel.ls.control.http.contents.CssContent;
import pt.isel.ls.control.http.contents.HtmlContent;
import pt.isel.ls.model.data.entities.Entity;
import pt.isel.ls.utils.Formatter;
import pt.isel.ls.utils.InfoTable;
import pt.isel.ls.utils.ReadFile;
import pt.isel.ls.utils.html.HTMLRef;

import java.util.stream.Stream;


public abstract class View<T extends Entity> {

    public abstract String getHtml(Stream<T> entities);

    public String getPlain(Stream<T> entities) {
        StringBuilder res = new StringBuilder();
        entities.forEach(en -> res.append(en.getPlaintText()).append("\n"));
        return res.toString();
    }

    public String getJson(Stream<T> entities) {
        StringBuilder res = new StringBuilder("[");
        entities.forEach(en -> res.append(en.getJson()).append("\n"));
        res.append("]");
        return res.toString();
    }

    protected static String readHtmlFromFIle(String filePath) {
       return ReadFile.readFromFile(filePath);
    }
    protected Formatter _formatter = new Formatter();
    protected String translateStream(Stream<T> entities) {
        StringBuilder trs = new StringBuilder();

        entities.forEach(e ->
                trs.append(getEntityHtml(e)));
        return trs.toString();
    }

    protected InfoTable infoTable;

    public void setInfoTable(InfoTable infos) {
        this.infoTable = infos;
    }

    protected StringBuilder buildPaging(InfoTable table) {
        StringBuilder pagination = new StringBuilder();
        if (table == null)
            return pagination;
        int skip = table.getSkip();
        int top = table.getTop();
        if (top == 0) return pagination;
        //prev
        if (skip - top >= 0)
            pagination.append(new HTMLRef(table.getPath() + "?skip=" + (skip - top) + "&top=" + top + " class=\"button\"").With("Prev"));
        //next
        if (skip + top < table.getMaxvalue())
        pagination.append(new HTMLRef(table.getPath() + "?skip=" + (skip + top) + "&top=" + top + " class=\"button\"").With("Next"));

        return pagination;
    }

    protected abstract String getEntityHtml(T course); // ???

    public static IHttpContent load(String viewPath) {
        return new HtmlContent(readHtmlFromFIle(viewPath));
    }

    public static IHttpContent load(String viewPath, String... dataToFormat) {
        return new HtmlContent(String.format(readHtmlFromFIle(viewPath), dataToFormat));
    }
    public static IHttpContent locadCss(String viewPath) {
        return new CssContent(readHtmlFromFIle(viewPath));
    }
}
