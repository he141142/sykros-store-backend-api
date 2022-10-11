package com.example.sykrosstore.helper.fileHelper;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.core.io.ClassPathResource;

public class FileModule {
  String fileName;
  File file;
  String path;


  public FileModule(FileModuleBuilder builder) {
    this.file = builder.file;
    this.fileName = builder.fileName;
    this.path = builder.path;
  }

  public static List<String> getSubFolderNameFromDirectory(String path) {
    File file = new File(path);
    String[] directories =
        file.list(
            (current, name) -> new File(current, name).isDirectory());
    assert directories != null;
    return Arrays.stream(directories).collect(Collectors.toList());
  }

  public JsonArray getJSONArrayFromFile() throws FileNotFoundException, ParseException {
    String path =
        new ClassPathResource(this.path+this.fileName)
            .getPath();
    FileReader reader = new FileReader(path);
    JsonArray parser =  JsonParser.parseReader(reader).getAsJsonArray();
    System.out.println(parser);

    return parser;
  }

  public JsonObject getJsonElement (int index,JsonArray obj){
    System.out.println(obj.get(index).getAsJsonObject());
        return obj.get(index).getAsJsonObject();
  }

//  public JSONArray getJSONArray() throws IOException {
//    String path =
//        new ClassPathResource(this.path+this.fileName)
//            .getPath();
//    JSONParser jsonParser = new JSONParser(this.getFileBytes(path));
//    JSONArray root = (JSONArray) ;
//  }

  public byte[] getFileBytes(String path) throws IOException {
    return Files.readAllBytes(new File(path).toPath());
  }



  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public static class FileModuleBuilder {
    String fileName;
    File file;
    String path;
    public FileModuleBuilder setFileName(String fileName) {
      this.fileName = fileName;
      return this;
    }

    public FileModuleBuilder setFile(File file) {
      this.file = file;
      return this;
    }
    public FileModuleBuilder setPath(String path) {
      this.path = path;
      return this;
    }
    public FileModule build() {
      return new FileModule(this);
    }
  }


}
