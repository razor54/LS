package pt.isel.ls.control.http;

public enum HttpStatusCode {
    Ok(200),
    Created(201),
    SeeOther(303),
    BadRequest(400),
    NotFound(404),
    MethodNotAllowed(405),
    InternalServerError(500),;

    private final int _code;

    HttpStatusCode(int code) {
        _code = code;
    }

    public int valueOf() {
        return _code;
    }

    public boolean isBadUsage() {
        return this == BadRequest ||this ==NotFound|| this == MethodNotAllowed;
    }
}
