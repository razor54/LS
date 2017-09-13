package pt.isel.ls.Result;


public class BoolResult implements IResult {
    @Override
    public String getResult() {
        return success ? "true" : "false";
    }

    private boolean success;

    public BoolResult(boolean success) {
        this.success = success;

    }


}
