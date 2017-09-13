package pt.isel.ls.model.data.dtos;

import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.data.entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static pt.isel.ls.control.http.HttpStatusCode.Ok;
import static pt.isel.ls.model.data.dtos.DTOType.Data;


public class MultiTableDto<T extends Entity> extends DTO {

    private final Function<HashMap<String, UniqueTableDto>, List<T>> toEntity;
    private final HashMap<String, UniqueTableDto> map;
    private String path;

    public MultiTableDto(Function<HashMap<String, UniqueTableDto>, List<T>> toEntity, HashMap<String, UniqueTableDto> map) {
        super(Ok, Data);
        this.toEntity = toEntity;
        this.map = map;
    }

    public MultiTableDto(Function<HashMap<String, UniqueTableDto>, List<T>> toEntity, HashMap<String, UniqueTableDto> map, String path) {
        super(Ok, Data);
        this.toEntity = toEntity;
        this.map = map;
        this.path = path;
    }

    public MultiTableDto(Function<HashMap<String, UniqueTableDto>, List<T>> toEntity, HashMap<String, UniqueTableDto> map, String path, HttpStatusCode code) {
        super(code, Data);
        this.toEntity = toEntity;
        this.map = map;
        this.path = path;
    }

    public MultiTableDto(HashMap<String, UniqueTableDto> map,HttpStatusCode ok) {
        super(ok, Data);
        this.map = map;
        toEntity = null;
    }

    // necessario criar uma lista com os valores


    @Override
    public int getRowNumber() {
        return map.get("main").getRowNumber();
    }

    @Override
    public int maxRowNumber() {
        return map.get("main").maxRowNumber();
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
    public String getJson() {
        StringBuilder res = new StringBuilder("{");
        //  getAsEntities().forEach( e-> res.append(e.getJson()));
        return res.append("}").toString();
    }

    @Override
    public String getPlainText() {
        StringBuilder res = new StringBuilder();
        //  getAsEntities().forEach( e-> res.append(e.getPlaintText()));
        return res.toString();
    }

    @Override
    public String getViewPath() {
        return path;
    }

    public HashMap<String, UniqueTableDto> getMap() {
        return map;
    }
}
