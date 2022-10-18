package com.example.sykrosstore.helper.gson;

import com.example.sykrosstore.helper.fileHelper.FileModule;
import com.example.sykrosstore.helper.reflect.ReflectCustom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.*;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;

class fileUt {
    String fileName;
    String fileLocation;

    fileUt(String fileName, String fileLocation) {
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}

public class GsonModule<T> implements GsonModuleInterface {

    fileUt fileUt;
    final Class<T> t;
    Gson gson;
    JSONArray JsonFromFile;
    List<String> fieldNames;
    String fileLocation;
    Map<String, String> keyMapper = new HashMap();

    public GsonModule(Class<T> typeParam) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
        this.t = typeParam;
        this.fieldNames = new ArrayList<>();
    }

    private void InitKeyMapper(String[][] mapper) {
        for (int i = 0; i < mapper.length; i++) {
            this.keyMapper.put(mapper[i][0], mapper[i][1]);
        }
    }

    public GsonModule(SykrosGsonBuilder builder) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
        this.t = builder.t;
        this.fileUt = builder.fileUt;
        InitKeyMapper(builder.getFieldsOfClass);
        PrintMapper();
    }

    public void deserialize() throws FileNotFoundException, ParseException, JSONException {
        this.gson = new GsonBuilder().registerTypeAdapter(this.t, new Deserializer<T>()).create();
        JsonArray jArr = this.getJSONArrayFromFile();
        for (int i = 0; i < jArr.size(); ++i) {
            JsonObject obj = jArr.get(i).getAsJsonObject();
            gson.fromJson(obj, this.t);
        }
    }

    public void PrintMapper() {
        for (Map.Entry<String, String> item : this.keyMapper.entrySet()) {
            System.out.println("Key: " + item.getKey()
                    + " Value: " + item.getValue());
        }
    }

    @Override
    public JsonObject getJSONObjectFromFile(int index) throws FileNotFoundException, ParseException {
        FileModule fm =
                new FileModule.FileModuleBuilder()
                        .setPath(this.fileUt.getFileLocation())
                        .setFileName(this.fileUt.getFileName())
                        .build();
        return fm.getJsonElement(index, fm.getJSONArrayFromFile());
    }

    @Override
    public JsonArray getJSONArrayFromFile() throws FileNotFoundException, ParseException {
        FileModule fm =
                new FileModule.FileModuleBuilder()
                        .setPath(this.fileUt.getFileLocation())
                        .setFileName(this.fileUt.getFileName())
                        .build();
        return fm.getJSONArrayFromFile();
    }

    public static class SykrosGsonBuilder<T> {
        List<String> fieldNames = new ArrayList<>();
        Class<T> t;
        String fileLocation;
        String[][] getFieldsOfClass = null;
        fileUt fileUt;

        public SykrosGsonBuilder pluginFile(String fileName, String fileLocation) {
            this.fileUt = new fileUt(fileName, fileLocation);
            return this;
        }

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

        public SykrosGsonBuilder initKeyMapper(String[][] keyMapper) {
            List<String> fieldOfClass = ReflectCustom.getFieldsOfClass(this.t);
            int arrSize = fieldOfClass.size();
            this.getFieldsOfClass = new String[arrSize][2];
            for (int j = 0; j < keyMapper.length; j++) {
                for (int k = 0; k < keyMapper[j].length; k++) {
                    final String key = keyMapper[j][k];
                    if (Arrays.stream(fieldOfClass.toArray()).anyMatch(str -> str.toString().equalsIgnoreCase(key))) {
                        this.getFieldsOfClass[j][0] = fieldOfClass.get(j);
                        this.getFieldsOfClass[j][1] = key;
                    } else {
                        this.getFieldsOfClass[j][0] = fieldOfClass.get(j);
                        this.getFieldsOfClass[j][1] = fieldOfClass.get(j);
                    }
                }
            }
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
