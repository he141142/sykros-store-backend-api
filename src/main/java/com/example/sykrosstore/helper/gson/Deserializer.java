package com.example.sykrosstore.helper.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class Deserializer<T> implements JsonDeserializer<T> {
  private Class<T> t;

  @Override
  public T deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    try {
      T tInstance = t.getDeclaredConstructor(t).newInstance();
      JsonArray jsonArray = jsonElement.getAsJsonArray();
      for (int i =0;i<jsonArray.size();i++){
        JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();
        System.out.println(jsonObj);
      }
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }
}
