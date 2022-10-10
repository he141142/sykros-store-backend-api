package com.example.sykrosstore.helper.fileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
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

  public JSONArray getJSONArrayFromFile() throws FileNotFoundException, ParseException {
    String path =
        new ClassPathResource(this.path+this.fileName)
            .getPath();
    FileReader reader = new FileReader(path);
    JSONParser jsonParser = new JSONParser(reader);
    Object obj = jsonParser.parse();
    return (JSONArray) obj;
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
