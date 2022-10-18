package com.example.sykrosstore.configuration.InitialLoad;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

//@Configuration
//@ImportResource("classpath:role.xml")
@Component
public class RoleInitialLoad extends InitialLoad {
  String location;
  String fileName;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
