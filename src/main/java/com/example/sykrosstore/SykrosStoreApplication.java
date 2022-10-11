package com.example.sykrosstore;

import com.example.sykrosstore.test.TestModule;
import java.io.FileNotFoundException;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;

@SpringBootApplication
public class SykrosStoreApplication {

  public static void main(String[] args)
      throws FileNotFoundException, JSONException, ParseException {
    TestModule t = new TestModule();
    t.process();
//    SpringApplication.run(SykrosStoreApplication.class, args);
  }

}
