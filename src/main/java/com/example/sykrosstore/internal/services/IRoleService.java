package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.entities.Role;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

public interface IRoleService {

    List<Role> initialLoad() throws FileNotFoundException, ParseException;

}
