package com.example.sykrosstore.helper.reflect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldObject {
  String name;
  String referenceFieldName;
  Class<?> fieldType;
  Object value;


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


}
