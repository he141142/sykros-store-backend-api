package com.example.sykrosstore.internal.controller;


import com.example.sykrosstore.constants.common.controller.AdminPrefix;
import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.internal.services.IRoleService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@AdminPrefix
public class AdminController {

    IRoleService roleService;

    public AdminController(@Autowired IRoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    public List<Role> loadRoles() throws FileNotFoundException, ParseException {
        return this.roleService.initialLoad();
    }
}
