package com.example.sykrosstore.helper.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class Deserializer<T> implements JsonDeserializer<T> {
  private Class<T> t;

  @Override
  public T deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    try {
      JsonObject j = jsonElement.getAsJsonObject();
      System.out.println(j);
    } catch (Exception
        e) {
      e.printStackTrace();
    }
    return null;
  }
}
