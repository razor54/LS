package pt.isel.ls.Result;

public class GetResult implements IResult<String> {

    private String result;

    public GetResult(String result) {
        this.result = result;
    }

    @Override
    public String getResult() {
        return result;
    }


}
