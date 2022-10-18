package com.example.sykrosstore.helper.gson;

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
            System.out.println(des);
        } catch (Exception
                e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class DeserializerBuilder {
        Map<String, String> mappedKey = new HashMap<>();

        public DeserializerBuilder addKeyMapper(String fieldToMap, String mapper) {
            this.mappedKey.put(fieldToMap, mapper);
            return this;
        }

        public Deserializer build() {
            return new Deserializer(this);
        }
    }
}
