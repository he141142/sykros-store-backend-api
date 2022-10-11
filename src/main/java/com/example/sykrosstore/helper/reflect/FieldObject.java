package com.example.sykrosstore.helper.reflect;

import java.util.List;

public class FieldObject {
  String name;
  Class<?> fieldType;
  Object value;
  List<String> keyMapInJson;

  public FieldObject(String name,Object value){
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Class<?> getFieldType() {
    return fieldType;
  }

  public void setFieldType(Class<?> fieldType) {
    this.fieldType = fieldType;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public List<String> getKeyMapInJson() {
    return keyMapInJson;
  }

  public void setKeyMapInJson(List<String> keyMapInJson) {
    this.keyMapInJson = keyMapInJson;
  }
}
