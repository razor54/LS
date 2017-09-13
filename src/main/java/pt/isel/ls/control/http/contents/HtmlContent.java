package pt.isel.ls.control.http.contents;

import pt.isel.ls.control.http.IHttpContent;

import java.io.IOException;
import java.io.Writer;


public class HtmlContent implements IHttpContent {
    private final String data;

    public HtmlContent(String data) {
        this.data = data;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(data);
    }

    @Override
    public String getMediaType() {
        return "text/html";
    }
}
