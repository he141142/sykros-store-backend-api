package com.example.sykrosstore.helper.gson;

import com.example.sykrosstore.helper.fileHelper.FileModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONArray;

public class GsonModule<T> implements GsonModuleInterface {
  final Class<T> t;
  Gson gson;
  JSONArray JsonFromFile;
  List<String> fieldNames;
  String fileName;


  public GsonModule(Class<T> typeParam) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    this.gson = gsonBuilder.create();
    this.t = typeParam;
    this.fieldNames = new ArrayList<>();
  }

  public GsonModule(SykrosGsonBuilder builder) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    this.gson = gsonBuilder.create();
    this.t = builder.t;
  }

  public void deserialize() {
    this.gson = new GsonBuilder().registerTypeAdapter(this.t, new Deserializer<T>()).create();
    gson.fromJson()
  }

  @Override
  public JSONArray getJSONArrayFromFile() throws FileNotFoundException, ParseException {
    return new FileModule.FileModuleBuilder().setPath();
  }

  public static class SykrosGsonBuilder<T> {
    List<String> fieldNames = new ArrayList<>();
    Class<T> t;

    public SykrosGsonBuilder SetClass(Class<T> typeParam) {
      this.t = typeParam;
      return this;
    }

    public SykrosGsonBuilder addField(String fieldName) {
      this.fieldNames.add(fieldName);
      return this;
    }

    public SykrosGsonBuilder SetFields(ArrayList<String> fields) {
      this.fieldNames = fields;
      return this;
    }

    public GsonModule build() {
      return new GsonModule(this);
    }

    public GsonModule SetClassBuilder(Class<T> t) {
      this.t = t;
      return new GsonModule(this);
    }
  }
}
