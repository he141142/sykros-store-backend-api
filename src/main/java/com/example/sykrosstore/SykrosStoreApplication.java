package com.example.sykrosstore;

import com.example.sykrosstore.configuration.ImportResourceService;
import com.example.sykrosstore.configuration.InitialLoad.Constants;
import com.example.sykrosstore.configuration.MainConfig;
import com.example.sykrosstore.test.TestModule;

import java.io.FileNotFoundException;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SykrosStoreApplication {
    public static void main(String[] args)
            throws FileNotFoundException, JSONException, ParseException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        AbstractApplicationContext abCtx = new ClassPathXmlApplicationContext("role.xml");
        ImportResourceService importResourceService = ctx.getBean(ImportResourceService.class);
        ImportResourceService importResourceServiceXML = (ImportResourceService) abCtx.getBean(ImportResourceService.class);
        System.out.println(importResourceServiceXML.getInitialFilePath(Constants.LOAD_TYPE.ROLE));
        importResourceServiceXML.getInitialFilePath(Constants.LOAD_TYPE.ROLE);
        new TestModule().process();
        SpringApplication.run(SykrosStoreApplication.class, args);
    }

}
