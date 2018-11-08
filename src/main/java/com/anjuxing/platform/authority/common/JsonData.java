package com.anjuxing.platform.authority.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
public class JsonData {

    private boolean ret;

    private String msg;

    private Object data;

    public JsonData(boolean ret){
        this.ret = ret;
    }

    public  static JsonData success(Object object , String msg){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    public  static JsonData success(Object object ){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    public  static JsonData success( ){
        JsonData jsonData = new JsonData(true);
        return jsonData;
    }

    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return  jsonData;
    }

    public static JsonData fail(Object data, String msg ){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        jsonData.data = data;
        return  jsonData;
    }

    public Map<String ,Object> toMap(){
        Map<String ,Object> result = new HashMap<>();
        result.put("ret",ret);
        result.put("data",data);
        result.put("msg",msg);
        return  result;
    }
}
