package com.example.sykrosstore.helper.gson;

import com.example.sykrosstore.helper.reflect.ReflectCustom;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.jboss.jandex.PrimitiveType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            T in = t.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, String> mapperI : this.mappedKey.entrySet()) {
//                Object itemVal = (Object)j.get(mapperI.getValue());
                Object itemVal = GsonHelper.castJSONPrimitive(j.get(mapperI.getValue()));
                ReflectCustom
                        .getTypeOfField(t, mapperI.getKey());
                System.out.println(ReflectCustom
                        .getTypeOfField(t, mapperI.getKey()).getName());
                if (itemVal != null) {
                    Object typePrimitive = ReflectCustom
                            .getTypeOfField(t, mapperI.getKey());

                    ReflectCustom.callSetter(in, mapperI.getKey(),itemVal);
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
