package com.example.sykrosstore.helper.gson;

import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class GsonHelper {
    /**
    * Opps We need A module to support type casting
    * */
    public Object castJSONPrimitive(JsonObject jsonObject,Class<?> primitive){
        if (primitive.isPrimitive()){
            Type
            return jsonObject.getAs
        }
    }
}
