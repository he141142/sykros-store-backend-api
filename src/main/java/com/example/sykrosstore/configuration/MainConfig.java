package com.example.sykrosstore.configuration;

import com.example.sykrosstore.configuration.InitialLoad.RoleInitialLoad;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RoleInitialLoad.class,ModelMapperConfig.class})
public class MainConfig {

  @Bean
  public ImportResourceService importResourceService() {
    return new ImportResourceService();
  }

  @Bean(name = {"helloMsg"})
  public String getHello() {
    return "Hello";
  }
}
