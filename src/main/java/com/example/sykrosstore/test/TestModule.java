package com.example.sykrosstore.test;

import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.helper.gson.GsonModule;
import com.example.sykrosstore.helper.gson.GsonModule.SykrosGsonBuilder;
import com.example.sykrosstore.helper.reflect.ReflectCustom;
import java.util.ArrayList;

public class TestModule {


    public void process(){
        GsonModule gsonRole = new SykrosGsonBuilder<Role>().SetClass(Role.class).SetFields(
            (ArrayList<String>) ReflectCustom.getFieldsOfClass(Role.class)).build();
        gsonRole.deserialize();
    }



}
