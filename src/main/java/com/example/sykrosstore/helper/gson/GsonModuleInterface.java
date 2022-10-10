package com.example.sykrosstore.helper.gson;

import java.io.FileNotFoundException;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONArray;

public interface GsonModuleInterface {
  public JSONArray getJSONArrayFromFile() throws FileNotFoundException, ParseException;
}
