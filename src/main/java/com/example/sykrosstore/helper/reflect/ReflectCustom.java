package com.example.sykrosstore.helper.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectCustom {
  public static Object callGetter(Object obj, String fieldName) {
    PropertyDescriptor pd;
    try {
      pd = new PropertyDescriptor(fieldName, obj.getClass());
      System.out.println("" + pd.getReadMethod().invoke(obj));
      return pd.getReadMethod().invoke(obj);
    } catch (InvocationTargetException | IllegalAccessException | IntrospectionException e) {
      e.printStackTrace();
      return "Error";
    }
  }

  public static List<String> getFieldsOfClass(Class<?> t){
    List<String> listFieldNames;
    listFieldNames = Arrays.stream(t.getFields()).map(
        Field::getName
    ).collect(Collectors.toList());
    return listFieldNames;
  }

  public static <T> T convertInstanceOfObject(Object o) {
    try {
      return (T) o;
    } catch (ClassCastException e) {
      return null;
    }
  }

  public static void callSetter(Object obj, String fieldName, Object value) {
    PropertyDescriptor pd;
    try {
      pd = new PropertyDescriptor(fieldName, obj.getClass());
      pd.getWriteMethod().invoke(obj, value);

    } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

}
