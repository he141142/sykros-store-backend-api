package com.example.sykrosstore.helper.gson;

import com.example.sykrosstore.helper.reflect.ReflectCustom;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Deserializer<T> implements JsonDeserializer<T> {
    private Class<T> t;
    Map<String, String> mappedKey;

    public Deserializer(DeserializerBuilder deserializerBuilder) {
        this.mappedKey = deserializerBuilder.mappedKey;
        this.t = deserializerBuilder.t;
    }

    public Deserializer() {
    }

    @Override
    public T deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        try {
            JsonObject j = jsonElement.getAsJsonObject();
            String des = String.valueOf(j.get("description"));

            System.out.println("Entry deserializer");
            System.out.println(des);
            T in = t.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, String> mapperI : this.mappedKey.entrySet()) {
                Object itemVal = j.get(mapperI.getValue());
                System.out.println(mapperI.getValue());
                System.out.println("it val");
                if (itemVal != null){
                    System.out.println(itemVal.toString());
                    ReflectCustom.callSetter(in, mapperI.getKey(), ReflectCustom
                            .getTypeOfField(t, mapperI.getKey())
                            .cast((Object) itemVal));
                }

            }
            return in;
        } catch (Exception
                e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class DeserializerBuilder<T> {
        Map<String, String> mappedKey = new HashMap<>();
        Class<T> t;

        public DeserializerBuilder addKeyMapper(String fieldToMap, String mapper) {
            this.mappedKey.put(fieldToMap, mapper);
            return this;
        }

        public DeserializerBuilder setKeyMapper(Map<String, String> mappedKey) {
            this.mappedKey = mappedKey;
            return this;
        }

        public Deserializer build(Class<T> t) {
            this.t = t;
            return new Deserializer(this);
        }
    }
}
