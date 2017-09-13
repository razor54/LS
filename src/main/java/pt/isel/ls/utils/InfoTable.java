package pt.isel.ls.utils;

import java.util.HashMap;

/**
 * @author andre_000
 *         on 6/17/2017.
 */
public class InfoTable {
    private final String path;
    private final int skip,top,maxvalue;

    public InfoTable(String path,int skip,int top,int maxValue){
        this.maxvalue=maxValue;
        this.path=path;
        this.skip=skip;
        this.top=top;
    }
    public InfoTable(HashMap<String,String>map){
        this.path=map.get("UserInput");
        this.top=Integer.parseInt(map.get("top"));
        this.skip=Integer.parseInt(map.get("skip"));
        this.maxvalue=-1;
    }

    public InfoTable(HashMap<String, String> map, int rowNumber) {
        this.path=map.get("UserInput");
        this.top=Integer.parseInt(map.get("top"));
        this.skip=Integer.parseInt(map.get("skip"));
        maxvalue = rowNumber;
    }


    public String getPath() {
        return path;
    }

    public int getSkip() {
        return skip;
    }

    public int getTop() {
        return top;
    }

    public int getMaxvalue() {
        return maxvalue;
    }
}
