package pt.isel.ls.control.http;

import pt.isel.ls.model.Writable;

public interface IHttpContent extends Writable {
    String getMediaType();
}
