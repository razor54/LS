package pt.isel.ls.control.http.responses;

import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.control.http.IHttpContent;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final HttpStatusCode _status;
    private final IHttpContent _body;
    private Charset _charset=Charset.forName("UTF-8");
    private final Map<String, String> _headers = new HashMap<String, String>();
    private final String redirecting;

    public HttpResponse(HttpStatusCode status) {

        _status = status;
        _body = null;
        redirecting = null;
    }

    public HttpResponse(HttpStatusCode status, IHttpContent body) {
        _status = status;
        _body = body;
        redirecting = null;
    }

    public HttpResponse(HttpStatusCode code, String path) {
        _status = code;
        _body = null;
        redirecting = path;
    }


    public HttpStatusCode getStatusCode() {
        return _status;
    }

    public HttpResponse withHeader(String name, String value) {
        _headers.put(name, value);
        return this;
    }

    public void send(HttpServletResponse resp) throws IOException {
        for (Map.Entry<String, String> entry : _headers.entrySet()) {
            resp.addHeader(entry.getKey(), entry.getValue());
        }
        if (_status == HttpStatusCode.SeeOther) {
            resp.sendRedirect(redirecting);
        } else {
            if (_body == null) {
                sendWithoutBody(resp);
            } else {
                sendWithBufferedBody(resp);
            }
        }
    }

    private void sendWithoutBody(HttpServletResponse resp) throws IOException {
        resp.setStatus(_status.valueOf());
    }

    private void sendWithBufferedBody(HttpServletResponse resp) throws IOException {
        _charset= Charset.forName(resp.getCharacterEncoding());
        ByteArrayOutputStream _os = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(_os, _charset))) {
            _body.writeTo(writer);
        }
        byte[] bytes = _os.toByteArray();
        resp.setStatus(_status.valueOf());
        String ctype = String.format("%s;charset=%s", _body.getMediaType(), _charset.name());
        resp.setContentType(ctype);
        resp.setContentLength(bytes.length);
        OutputStream ros = resp.getOutputStream();
        ros.write(bytes);
        ros.close();
    }

    public String getInfo() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(b, _charset))) {
            _body.writeTo(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new String(b.toByteArray(), _charset);

    }

  /*  public Scanner ScannedInfo() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintWriter p = new PrintWriter(b);
        _body.writeTo(p);

        b.toString()

    }*/
}
