package pt.isel.ls.model.data.dtos;

import pt.isel.ls.control.http.HttpStatusCode;

import static pt.isel.ls.model.data.dtos.DTOType.Info;



public class StaticDto extends DTO {
    private final String filepath;

    public StaticDto(String filepath) {
        super(HttpStatusCode.Ok, Info);
        this.filepath = filepath;
    }


    @Override
    public int getRowNumber() {
        return 0;
    }

    @Override
    protected String getPlainRowAt(int idx) {
        return null;
    }

    @Override
    protected String getJSONFormatOf(int i) {
        return null;
    }

    @Override
    public String getViewPath() {
        return filepath;
    }
}
