package com.example.sykrosstore.helper.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jboss.jandex.PrimitiveType;
import org.jboss.jandex.Type;

public class GsonHelper {
    /**
    * Opps We need A module to support type casting
    * */
    public static Object castJSONPrimitive(final Object o){
        if (o==null){
            return null;
        }
        if(!(o instanceof JsonElement)){
            return o;
        }
        JsonElement e = (JsonElement) o;
        if (e.isJsonNull()){
            return null;
        }
        if (e.isJsonPrimitive()){
            JsonPrimitive jp = e.getAsJsonPrimitive();
            if (jp.isString()){
                return jp.getAsString();
            }
            if (jp.isNumber()){
                return jp.getAsNumber().intValue();
            }
            if (jp.isBoolean()){
                return jp.getAsBoolean();
            }
        }
        return o;
    }
}
