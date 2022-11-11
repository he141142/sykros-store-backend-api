package com.example.sykrosstore.internal.controller;


import com.example.sykrosstore.constants.common.controller.AdminPrefix;
import com.example.sykrosstore.constants.common.controller.advice.DatabaseOperationException;
import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.internal.services.AuthService;
import com.example.sykrosstore.internal.services.IRoleService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@AdminPrefix
public class AdminController {

    IRoleService roleService;

    AuthService authService;

    public AdminController(@Autowired IRoleService roleService,
    @Autowired AuthService auth_service) {
        this.roleService = roleService;
        this.authService = auth_service;
    }

    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    public List<Role> loadRoles() throws FileNotFoundException, ParseException {
        return this.roleService.initialLoad();
    }

    @RequestMapping(value = "/clear-users/{userName}",method = RequestMethod.DELETE)
    public ResponseEntity<?> clearUser(
            @PathVariable(name = "userName")  String userName
    ) throws DatabaseOperationException {
        return new ResponseEntity<>(this.authService.clearUserByUserName(userName), HttpStatus.OK);
    }
}
