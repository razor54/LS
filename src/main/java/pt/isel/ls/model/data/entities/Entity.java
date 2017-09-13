package pt.isel.ls.model.data.entities;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import pt.isel.ls.utils.html.HTMLElement;
import pt.isel.ls.utils.html.HTMLTr;

import java.util.HashMap;


public abstract class Entity {
    protected static SQLServerDataSource connection = null;
    protected final String[] tableHeaders;// = {MANDATORY,NAME,ACRONYM,COORDINATOR};
    protected HashMap<String, String> values;
        public Entity(String[] tableHeaders){
            this.tableHeaders = tableHeaders;
            values = new HashMap<>();
            values.put("top", "10");
            values.put("skip", "0");
            values.put("accept", "text/plain");
        }

    public static void setDataSource(SQLServerDataSource dataSource) {
        connection = dataSource;
    }

    public abstract HTMLElement getHtmlElement();

    public abstract String getJson();

    public abstract String getPlaintText();

    public final String[] getTableHeaders(){
        return tableHeaders;
    }

    public HTMLTr<String> getHTMLHeaders(){
        HTMLTr<String> row = new HTMLTr<>();
        for (String tableHeader : tableHeaders) {
            row.addHeader(tableHeader);
        }
        return row;
    }

}
