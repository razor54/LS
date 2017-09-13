package pt.isel.ls.control.http.contents;

import pt.isel.ls.control.http.IHttpContent;

import java.io.IOException;
import java.io.Writer;


public class CssContent implements IHttpContent {
    private final String data;

    public CssContent(String data) {
        this.data = data;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(data);
    }

    @Override
    public String getMediaType() {
        return "text/css";
    }
}
