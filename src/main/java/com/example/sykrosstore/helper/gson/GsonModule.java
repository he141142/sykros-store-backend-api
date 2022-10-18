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


/**

    public void deserialize() throws FileNotFoundException, ParseException, JSONException {
        this.gson = new GsonBuilder().registerTypeAdapter(this.t, new Deserializer
                        .DeserializerBuilder()
                        .setKeyMapper(this.keyMapper)
                        .build())
                .create();
        JsonArray jArr = this.getJSONArrayFromFile();
        for (int i = 0; i < jArr.size(); ++i) {
            JsonObject obj = jArr.get(i).getAsJsonObject();
            gson.fromJson(obj, this.t);
        }
    }
*/

    public ArrayList<T> deserializeArray() throws FileNotFoundException, ParseException {
        ArrayList<T> genericList = new ArrayList<>();
        this.gson = new GsonBuilder().registerTypeAdapter(this.t, new Deserializer
                        .DeserializerBuilder()
                        .setKeyMapper(this.keyMapper)
                        .build(t))
                .create();
        JsonArray jArr = this.getJSONArrayFromFile();
        for (int i = 0; i < jArr.size(); ++i) {
            JsonObject obj = jArr.get(i).getAsJsonObject();
            T in = gson.fromJson(obj, this.t);
            genericList.add(in);
        }
        return genericList;
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

        public int getIndexByVal(String[][] keyMapper, String val) {
            for (int i = 0; i < keyMapper.length; ++i) {
                for (int j = 0; j < keyMapper[i].length; ++j) {
                    if (val.equalsIgnoreCase(keyMapper[i][j])) {
                        return i;
                    }
                }
            }
            return -1;
        }

        private Map<Integer, Integer> pickIdx(String[][] keyMapper) {
            Map<Integer, Integer> idxMapper = new HashMap<>();
            for (int i = 0; i < keyMapper.length; ++i) {
                int idxExist = getIndexByValue(0, keyMapper[i][0]);
                if (idxExist != -1) {
                    idxMapper.put(idxExist, i);
                }
            }
            return idxMapper;
        }

        public void printKeyMap() {
            System.out.println("----------------------dev-----------------------------");
            for (int k = 0; k < this.getFieldsOfClass.length; ++k) {
                System.out.println(this.getFieldsOfClass[k][0]);
                System.out.println(this.getFieldsOfClass[k][1]);
            }
        }

        private int getIndexByValue(int idx, String val) {
            if (idx == this.getFieldsOfClass.length) {
                return -1;
            }
            if (this.getFieldsOfClass[idx][0].equalsIgnoreCase(val)) {
                return idx;
            }
            return getIndexByValue(idx + 1, val);
        }

        private void initMapperDefault() {
            List<String> fieldOfClass = ReflectCustom.getFieldsOfClass(this.t);
            int arrSize = fieldOfClass.size();
            this.getFieldsOfClass = new String[arrSize][2];
            for (int i = 0; i < arrSize; ++i) {
                this.getFieldsOfClass[i][0] = fieldOfClass.get(i);
                this.getFieldsOfClass[i][1] = fieldOfClass.get(i);
            }
        }

        public SykrosGsonBuilder initKeyMapper(String[][] keyMapper) {
            initMapperDefault();
            if (keyMapper != null && (keyMapper.length != 0)) {
                Map<Integer, Integer> idxMapper = pickIdx(keyMapper);
                for (Map.Entry<Integer, Integer> item : idxMapper.entrySet()) {
                    this.getFieldsOfClass[item.getKey()] = keyMapper[item.getValue()];
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
