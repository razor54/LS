package pt.isel.ls.model.data.dtos;

import pt.isel.ls.control.http.HttpStatusCode;


public class MessageDto extends DTO {

    public MessageDto(String message,HttpStatusCode code ) {
        super(code,DTOType.Message);
        this.message = message;
    }

    @Override
    public int getRowNumber() {
        return 0;
    }

    @Override
    public String getPlainRowAt(int idx) {
        return null;
    }

    @Override
    public String getJSONFormatOf(int i) {
        return null;
    }

    @Override
    public String getViewPath() {
        return null;
    }
}
