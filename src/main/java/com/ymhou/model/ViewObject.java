package com.ymhou.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ymhou on 2017/3/25.
 */
public class ViewObject {
    private Map<String,Object> objs = new HashMap<>();

    public void set(String key, Object value){
        objs.put(key,value);
    }

    public Object get(String key){
        return objs.get(key);
    }
}
