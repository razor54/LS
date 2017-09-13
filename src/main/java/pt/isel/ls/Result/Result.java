package pt.isel.ls.Result;

import java.util.function.Function;


public class Result<K, V> implements IResult<V> {
    private final Function<K, V> map;
    private final K val;

    public Result(Function<K, V> map, K val) {
        this.map = map;
        this.val = val;
    }

    @Override
    public V getResult() {
        return map.apply(val);
    }

}
