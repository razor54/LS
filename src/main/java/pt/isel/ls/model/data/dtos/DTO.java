package pt.isel.ls.model.data.dtos;

import pt.isel.ls.control.http.HttpStatusCode;


public abstract class DTO<E> {

    String message;

    public String getMessage() {
        return message;
    }

    public String getJson() {
        StringBuilder result = new StringBuilder().append("{").append("[").append("\n");
        for (int i = 0; i < getRowNumber(); i++) {
            if (i > 0)
                result.append(",").append("\n");
            result.append(getJSONFormatOf(i));
        }
        return result.append("\n").append("]").append("}").toString();
    }


    public String getPlainText() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < getRowNumber(); i++) {
            res.append(getPlainRowAt(i)).append("\n");
        }
        return res.toString();
    }

    public HttpStatusCode getStatusCode() {
        return code;
    }

    public int maxRowNumber(){
        return -1;
    }


    private HttpStatusCode code;
    private final DTOType type;

    protected DTO(HttpStatusCode code, DTOType type) {
        this.code = code;
        this.type = type;
    }

    public abstract int getRowNumber();

    protected abstract String getPlainRowAt(int idx);

    protected abstract String getJSONFormatOf(int i);

    public abstract String getViewPath();

    public void setCode(HttpStatusCode changingTo) {
        code = changingTo;
    }

    public DTOType getType() {
        return type;
    }
}
