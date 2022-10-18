package com.example.sykrosstore.test;

import com.example.sykrosstore.configuration.ImportResourceService;
import com.example.sykrosstore.configuration.InitialLoad.Constants;
import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.helper.gson.GsonModule;
import com.example.sykrosstore.helper.gson.GsonModule.SykrosGsonBuilder;
import com.example.sykrosstore.helper.reflect.ReflectCustom;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestModule {
    public void process() throws FileNotFoundException, JSONException, ParseException {
        AbstractApplicationContext abCtx = new ClassPathXmlApplicationContext("role.xml");
        ImportResourceService importResourceServiceXML = (ImportResourceService) abCtx.getBean(ImportResourceService.class);
        GsonModule gsonRole = new SykrosGsonBuilder<Role>().SetClass(Role.class).SetFields(
                (ArrayList<String>) ReflectCustom.getFieldsOfClass(Role.class)).pluginFile(
                importResourceServiceXML.getFileName(Constants.LOAD_TYPE.ROLE),
                importResourceServiceXML.getFileLocation(Constants.LOAD_TYPE.ROLE)
        ).initKeyMapper(new String[][]{
                {"description", "name"}, {"description", "description"},
                {"authority", "authorities"}
        }).SetClassBuilder(Role.class);
        ArrayList<Role> roles = gsonRole.deserializeArray();
        printRoles(roles);

    }


    public void printRoles(ArrayList<Role> roles) {
        System.out.printf("%20s%20s%20s\n", "name", "authority", "description");
        for (int i = 0; i < roles.size(); i++) {
            System.out.printf("%20s%20s%20s\n", "name", "authority", "description");
        }
    }
}
