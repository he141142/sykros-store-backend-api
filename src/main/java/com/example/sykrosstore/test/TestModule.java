package com.example.sykrosstore.test;

import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.helper.gson.GsonModule;
import com.example.sykrosstore.helper.gson.GsonModule.SykrosGsonBuilder;
import com.example.sykrosstore.helper.reflect.ReflectCustom;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public class TestModule {


    public void process() throws FileNotFoundException, JSONException, ParseException {
        GsonModule gsonRole = new SykrosGsonBuilder<Role>().SetClass(Role.class).SetFields(
            (ArrayList<String>) ReflectCustom.getFieldsOfClass(Role.class)).SetClassBuilder(Role.class);
        gsonRole.deserialize();
    }



}
