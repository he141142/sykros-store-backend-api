package com.example.sykrosstore.helper.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONArray;

public interface GsonModuleInterface {
  public JsonObject getJSONArrayFromFile(int index) throws FileNotFoundException, ParseException;
}
