package com.example.sykrosstore.internal.services;

import com.example.sykrosstore.entities.Role;
import com.example.sykrosstore.internal.repositories.RoleRepository;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public List<Role> initialLoad() throws FileNotFoundException, ParseException {
        ArrayList<Role> roles = new Role().loadDefault();
        return (List<Role>) this.roleRepository.saveAll(roles);
    }

}
