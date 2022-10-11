package com.example.sykrosstore.helper.gson;

import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import org.apache.tomcat.util.json.ParseException;

public interface GsonModuleInterface {
   JsonObject getJSONArrayFromFile(int index) throws FileNotFoundException, ParseException;
}
